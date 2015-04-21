package com.ysmal.ssc.server.Impl;

import com.ysmal.ssc.server.Abstract.AbstractSource;
import com.ysmal.ssc.model.Vacancy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.StdDateFormat;

public class SourceHHapi implements AbstractSource {

    private String url;

    public SourceHHapi(String url) {
        this.url = url;
    }

    public List<Vacancy> getVacanciesList() {
        return getVacanciesList(this.url);
    }

    private List<Vacancy> getVacanciesList(String url) {
        try {
            List<Vacancy> vacancyList = new ArrayList<Vacancy>();
            
            String response = getHtmlResponse(url);
//            System.out.println("response=" + response);
            if (response.isEmpty()){return null;}
//JSON parsing
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);
            JsonNode vacancies = node.get("items");
            System.out.println("Всего найдено вакансий: " + node.get("found"));
            System.out.println("Всего страниц: " + node.get("pages"));
            if (vacancies == null){return null;}
            
//            System.out.println("vacancies "+vacancies);
    //get Vacancies from JSON
            Iterator<JsonNode> listNodes= vacancies.getElements();
            while (listNodes.hasNext()) {
                Vacancy tempVacancy = new Vacancy();
                JsonNode VacancyElement = listNodes.next();
//                System.out.println("Name="+VacancyElement.get("name").toString());
                tempVacancy.setName(getParsingAttribute(VacancyElement, "name"));
//                System.out.println("TempVacancy name="+tempVacancy.getName());
                tempVacancy.setId(VacancyElement.get("id").getValueAsLong());
                tempVacancy.setSalary("от "+VacancyElement.get("salary").get("from")+" до " + VacancyElement.get("salary").get("to")+" "+getParsingAttribute(VacancyElement.get("salary"),"currency"));
                tempVacancy.setRef(getParsingAttribute(VacancyElement,"alternate_url"));
                tempVacancy.setCompanyName(getParsingAttribute(VacancyElement.get("employer"),"name"));
                StdDateFormat df = new StdDateFormat();
                try {
                    Date resData = df.parse(getParsingAttribute(VacancyElement,"published_at"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    tempVacancy.setStringDate(dateFormat.format(resData));
                } catch (ParseException ex) {
//                    Logger.getLogger(SourceHHapi.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                tempVacancy.setSearchWord("Java");
                
                vacancyList.add(tempVacancy);
//                System.out.println("Name="+VacancyElement.get("name").toString());
//                System.out.println("Id="+VacancyElement.get("id"));
//                System.out.println("Salary from "+VacancyElement.get("salary").get("from")+" to " + VacancyElement.get("salary").get("to"));
//                System.out.println("Date="+VacancyElement.get("published_at"));
            }
//            tempVacancy = null;
            return vacancyList;
            
        } catch (IOException ex) {
            return null;
//            Logger.getLogger(SourceHHapi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private String getHtmlResponse(String url) throws IOException {
//        System.out.println("url="+url);
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

    private String getParsingAttribute(JsonNode VacancyElement, String name){
        if (VacancyElement.isNull()){return null;}
        if (!VacancyElement.get(name).isNull()){return VacancyElement.get(name).getTextValue();} 
        else return null;
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
}
