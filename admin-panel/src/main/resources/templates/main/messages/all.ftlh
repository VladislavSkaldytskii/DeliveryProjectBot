<#import "../../common/home.ftlh" as h>
<#import "../../common/table.ftlh" as t>

<@h.home "Сообщения">

  <@t.table "messages" "asc" />
  <table id="messages" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Номер</th>
        <th>Описание</th>
        <th>Текст</th>
        <th>Править</th>
      </tr>
    </thead>
    <tbody>
      <#list messages as message>
        <tr>
          <td>${message.id?c}</td>
          <td>${message.description!}</td>
          <td>${message.text}</td>
          <td>
            <a class="btn btn-xs btn-info command-edit" href="messages/edit/${message.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
