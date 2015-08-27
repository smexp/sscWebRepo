
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--general_info--%>
<div id="dialog" title="Внимание">
  <p> </p>
</div>

<%--filter_form--%>
<div id="filter" title="Задайте фильтр">
  <p class="validateTips">All form fields are required.</p>

  <form>
    <fieldset>
      <label for="companyName">Компания</label>
      <input type="text" name="companyName" id="companyName" value="Jane Smith" class="text ui-widget-content ui-corner-all">
      <label for="email">Email</label>
      <input type="text" name="email" id="email" value="jane@smith.com" class="text ui-widget-content ui-corner-all">

      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>