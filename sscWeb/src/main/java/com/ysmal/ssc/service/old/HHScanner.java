package com.ysmal.ssc.service;

import com.ysmal.ssc.model.ChangedAtribute;
import com.ysmal.ssc.model.Vacancy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

//@RunWith(MockitoJUnitRunner.class)
public class HHScanner extends AbstractScannerThread {

    private String SEARCH_WORD;
    private String CLASS_MAIN_PART_VALUE;
    private String CLASS_VACANCY_NAME_VALUE;
    private String CLASS_VACANCY_DATE_VALUE;
    private String CLASS_VACANCY_ADDRESS_VALUE;
    private String CLASS_VACANCY_SALARY_VALUE;
    private String BLOCK_COMPANY_INFO;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(HHScanner.class);

    //constructor
    public HHScanner(String searchWord, long timeLag) throws IOException {
        this.SEARCH_WORD = searchWord;
        Properties prop = new Properties();
        prop.load(this.getClass().getResourceAsStream("/hhParsing.properties"));
        this.url = prop.getProperty("url1") + this.SEARCH_WORD + prop.getProperty("url2");
        this.CLASS_MAIN_PART_VALUE = prop.getProperty("classMainPartValue");
        this.CLASS_VACANCY_NAME_VALUE = prop.getProperty("classVacancyNameValue");
        this.CLASS_VACANCY_DATE_VALUE = prop.getProperty("classVacancyDateValue");
        this.CLASS_VACANCY_ADDRESS_VALUE = prop.getProperty("classVacancyAddressValue");
        this.CLASS_VACANCY_SALARY_VALUE = prop.getProperty("classVacancySalaryValue");
        this.BLOCK_COMPANY_INFO = prop.getProperty("blockCompanyInfo");
        this.timeLag = timeLag;
        this.stopThread = false;
    }

    @Override
    public void run() {
        do {
            this.vacancyList = getVacancies(this.url); //add mock object
            this.historyList = new ArrayList();
            
            if (this.vacancyList != null) {
                if (this.vacancyList.size() > 0) {
                    saveOrUpdateVacanciesFromList(this.vacancyList); //add mock object
                    if (log.isInfoEnabled()) {
                        log.info("Scanning has ended");
                    }
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Vacancy list is null");
                }
            }

            synchronized (this) {
                try {
                    if (log.isInfoEnabled()) {
                        log.info("Wait...");
                    }
                    Thread.currentThread().wait(this.timeLag);
                } catch (InterruptedException ex) {
                }
            }
        } while (!this.isStopThread());
    }

    @Override
    List<Vacancy> getVacancies(String url) {

        // return html in response
        try {
            String response = getHtmlResponse(url);

            // Parsing html by tags
            org.jsoup.nodes.Document doc = Jsoup.parse(response);
            Elements mainPartListElements = doc.getElementsByAttributeValue("class", CLASS_MAIN_PART_VALUE);

            if (mainPartListElements.size() > 0) {
                if (log.isInfoEnabled()) {
                    log.info("Has found " + mainPartListElements.size() + " vacancies");
                }
                vacancyList = new ArrayList<Vacancy>();
                Iterator<Element> itVacancy = mainPartListElements.iterator();
                //iterator works under each records
                while (itVacancy.hasNext()) {
                    Vacancy vacancy = new Vacancy();
                    Element currentVacancy = itVacancy.next();
                    vacancy.setSearchWord(this.SEARCH_WORD);
                    vacancy.setName(currentVacancy.getElementsByAttributeValue("class", CLASS_VACANCY_NAME_VALUE).text());
                    vacancy.setRef(currentVacancy.getElementsByAttributeValue("class", CLASS_VACANCY_NAME_VALUE).attr("href"));
                    if (!vacancy.getRef().isEmpty()) {
                        vacancy.setId(getParsedId(vacancy.getRef()));
                    }
                    //----parsing ref and name company
                    String block = currentVacancy.getElementsByAttributeValue("class", BLOCK_COMPANY_INFO).html();
                    org.jsoup.nodes.Document docCompany = Jsoup.parse(block);
                    Elements elements = docCompany.getElementsByTag("a");

                    //-----------------------company

                    if (elements.size() > 0) {
                        vacancy.setCompanyName(elements.text());
                    }

                    //-----------------------end company
                    vacancy.setStringDate(currentVacancy.getElementsByAttributeValue("class", CLASS_VACANCY_DATE_VALUE).text());
                    vacancy.setAddress(currentVacancy.getElementsByAttributeValue("class", CLASS_VACANCY_ADDRESS_VALUE).text());
                    vacancy.setSalary(currentVacancy.getElementsByAttributeValue("class", CLASS_VACANCY_SALARY_VALUE).text());

                    vacancyList.add(vacancy);

                }
                return vacancyList;
            }

        } catch (IOException ex) {
            Logger.getLogger(HHScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    void saveOrUpdateVacanciesFromList(List<Vacancy> vacancyList) {
        log.info("Write to base");
        Session s = this.getSessionFactory().getCurrentSession();
        Calendar cal = Calendar.getInstance();
        s.beginTransaction();
        for (Vacancy r : vacancyList) {
            Vacancy rInBase = (Vacancy) s.get(Vacancy.class, r.getId());
            if (rInBase != null) {

                s.update(getMappedVacancy(rInBase, r, cal));
                if (!historyList.isEmpty()) {
                    Session h = this.getSessionFactory().openSession();
                    for (ChangedAtribute att : historyList) {
                        System.out.println("Save history " + att);
                        h.beginTransaction();
                        h.save(att);
                        h.getTransaction().commit();
                    }
                    historyList.clear();
                    h.close();
                }
            } else {
                r.setCreatedate(cal);
                r.setUpdatedate(cal);
                s.save(r);
            }
        }
        s.getTransaction().commit();
    }

    private String getHtmlResponse(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpRequest = new HttpGet(url);
        HttpResponse httpResponse;
        httpResponse = httpClient.execute(httpRequest);
        HttpEntity entity = httpResponse.getEntity();
        InputStream input = entity.getContent();
        String response = getStringFromInputStream(input);
        httpClient.close();
        return response;
    }

    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        return sb.toString();

    }

    private long getParsedId(String str) {
        StringBuilder strBuld = new StringBuilder(str);
        strBuld = new StringBuilder(strBuld.substring(strBuld.indexOf("vacancy/") + 8));
        Long id = new Long(strBuld.substring(0, strBuld.indexOf("?")));
        return id.longValue();
    }

    private SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    private Vacancy getMappedVacancy(Vacancy rInBase, Vacancy r, Calendar cal) {
        boolean isChangedAtributes = false;
        // make compare attributes
        ChangedAtribute recordHistory = new ChangedAtribute();
        if (rInBase.getAddress() != null && !rInBase.getAddress().equals(r.getAddress())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("address");
            recordHistory.setOldValue(rInBase.getAddress());
            recordHistory.setNewValue(r.getAddress());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;

            rInBase.setAddress(r.getAddress());

        }
        if (rInBase.getCompanyName() != null && !rInBase.getCompanyName().equals(r.getCompanyName())) {
            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("companyname");
            recordHistory.setOldValue(rInBase.getCompanyName());
            recordHistory.setNewValue(r.getCompanyName());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;

            rInBase.setCompanyName(r.getCompanyName());

        }
        if (rInBase.getLocation() != null && !rInBase.getLocation().equals(r.getLocation())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("location");
            recordHistory.setOldValue(rInBase.getLocation());
            recordHistory.setNewValue(r.getLocation());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setLocation(r.getLocation());

        }
        if (rInBase.getName() != null && !rInBase.getName().equals(r.getName())) {
            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("name");
            recordHistory.setOldValue(rInBase.getName());
            recordHistory.setNewValue(r.getName());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setName(r.getName());
        }
        if (rInBase.getRef() != null && !rInBase.getRef().equals(r.getRef())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("ref");
            recordHistory.setOldValue(rInBase.getRef());
            recordHistory.setNewValue(r.getRef());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setRef(r.getRef());

        }
        if (rInBase.getSalary() != null && !rInBase.getSalary().equals(r.getSalary())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("salary");
            recordHistory.setOldValue(rInBase.getSalary());
            recordHistory.setNewValue(r.getSalary());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setSalary(r.getSalary());

        }
        if (rInBase.getSearchWord() != null && !rInBase.getSearchWord().equals(r.getSearchWord())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("searchword");
            recordHistory.setOldValue(rInBase.getSearchWord());
            recordHistory.setNewValue(r.getSearchWord());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setSearchWord(r.getSearchWord());

        }
        if (rInBase.getStringDate() != null && !rInBase.getStringDate().equals(r.getStringDate())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("stringdate");
            recordHistory.setOldValue(rInBase.getStringDate());
            recordHistory.setNewValue(r.getStringDate());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setStringDate(r.getStringDate());

        }
        if (rInBase.getVacRef() != null && !rInBase.getVacRef().equals(r.getVacRef())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("vacref");
            recordHistory.setOldValue(rInBase.getVacRef());
            recordHistory.setNewValue(r.getVacRef());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setVacRef(r.getVacRef());

        }

        if (isChangedAtributes) {
            rInBase.setUpdatedate(cal);
        }
        recordHistory = null;
        return rInBase;
    }
}
