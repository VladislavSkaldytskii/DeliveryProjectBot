<#include "security.ftlh">

<nav class="navbar navbar-default">
  <div class="container-fluid">

    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed"
              data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Панель управления</a>
    </div>

    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li><a href="clients"><i class="fa fa-users mr-1"></i>Клиенты</a></li>
        <li><a href="categories"><i class="fa fa-folder-open mr-1"></i>Категории</a></li>
        <li><a href="products"><i class="fa fa-archive mr-1"></i>Продукты</a></li>
        <li><a href="orders"><i class="fa fa-cart-arrow-down mr-1"></i>Заказы</a></li>
        <li><a href="broadcasts/add"><i class="fa fa-comment mr-1"></i>Трансляция сообщения</a></li>
          <li><a href="couriers"><i class="fa fa-users mr-1"></i>Курьеры</a></li>

        <#if user?? && user.hasAnyAuthority('ADMIN')>
          <li><a href="messages"><i class="fa fa-envelope mr-1"></i>Сообщение</a></li>
          <li><a href="users"><i class="fa fa-user-secret mr-1"></i>Пользователи</a></li>
        </#if>
      </ul>

      <div class="navbar-text pull-right">
        <form action="logout" method="post">
          <input type="hidden" name="_csrf" value="${_csrf.token}" />
          <input type="submit" class="btn btn-xs btn-warning" value="Выйти"/>
        </form>
      </div>

      <div class="navbar-text pull-right">
        <i><#if user??>${user.username}</#if></i>
      </div>
    </div>

  </div>
</nav>
