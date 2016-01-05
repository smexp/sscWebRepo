
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--general_info--%>
<div id="dialog" title="Внимание">
  <p> </p>
</div>

<%--createDialog--%>
<div id="createDialog" title="Задайте параметры сканера">
  <p class="validateTips"></p>

  <form>
    <fieldset>
      <label for="searchWordParam">Параметр поиска</label>
      <input type="text" name="searchWordParam" id="searchWordParam" value="" class="text ui-widget-content ui-corner-all">

      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>