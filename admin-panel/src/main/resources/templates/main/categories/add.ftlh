<#import "../../common/home.ftlh" as h>

<@h.home "Добавить категорию">

  <form method="post" action="categories/create">
    <div class="form-group row">
      <label class="col-sm-2">Название:</label>
      <div class="col-sm-10">
        <input class="form-control" name="name" value="<#if category??>${category.name!}</#if>">
        <#if nameError??><div class="text-warning">${nameError}</div></#if>
      </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button class="btn btn-success" type="submit">Создать</button>
  </form>

  <form method="get" action="categories">
    <button class="btn btn-info" type="submit">Назад</button>
  </form>

</@h.home>
