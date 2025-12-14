<#import "../../common/home.ftlh" as h>

<@h.home "Редактирование курьеров">

  <#if courier??>
    <form method="post" action="couriers/update">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${courier.id?c}" readonly>
        </div>
      </div>



      <div class="form-group row">
        <label class="col-sm-2">Имя:</label>
        <div class="col-sm-10">
          <input class="form-control" name="name" value="${courier.name!}">
          <#if nameError??><div class="text-warning">${nameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Номер телефона:</label>
        <div class="col-sm-10">
          <input class="form-control" name="phoneNumber" value="${courier.phoneNumber!}">
          <#if phoneNumberError??><div class="text-warning">${phoneNumberError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Город:</label>
        <div class="col-sm-10">
          <input class="form-control" name="city" value="${courier.city!}">
          <#if cityError??><div class="text-warning">${cityError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Адрес:</label>
        <div class="col-sm-10">
          <input class="form-control" name="address" value="${courier.address!}">
          <#if addressError??><div class="text-warning">${addressError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Активность:</label>
        <div class="col-sm-10">
          <select class="form-control" name="active">
            <option value="true" <#if courier.active>selected</#if>>Yes</option>
            <option value="false" <#if !courier.active>selected</#if>>No</option>
          </select>
        </div>
      </div>

      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-success" type="submit">Обновить</button>
    </form>

    <form method="get" action="couriers">
      <button class="btn btn-info" type="submit">Назад</button>
    </form>


  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This courier does not exist!
    </div>
  </#if>

</@h.home>
