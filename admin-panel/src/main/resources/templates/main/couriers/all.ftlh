<#import "../../common/home.ftlh" as h>
<#import "../../common/table.ftlh" as t>

<@h.home "Курьеры">

    <center class="mb-4">
        <a class="btn btn-sm btn-info" href="couriers/create">Добавить курьера</a>
    </center>

  <@t.table "courier" />
  <table id="courier" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Номер</th>
        <th>Имя</th>
        <th>Город</th>
        <th>Активность</th>
        <th>Править</th>
      </tr>
    </thead>
    <tbody>
      <#list couriers as courier>
        <tr>
          <td>${courier.id?c}</td>
          <td>${courier.name!}</td>
          <td>${courier.city!}</td>
          <td><#if courier.active>Да<#else>Нет</#if></td>
          <td>
            <a class="btn btn-xs btn-info" href="couriers/edit/${courier.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
