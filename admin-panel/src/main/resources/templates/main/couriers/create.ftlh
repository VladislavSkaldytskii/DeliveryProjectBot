<#import "../../common/home.ftlh" as h>

<@h.home "Добавить курьера">

    <form method="post" action="couriers/create">
        <div class="form-group row">
            <label class="col-sm-2">Клиент:</label>
            <div class="col-sm-10">
                <select class="form-control" name="chatId" required>
                    <option value="" disabled selected>Select client</option>

                    <#list clients as client>
                        <option value="${client.chatId?c}">
                            ${client.name!} — ${client.chatId?c}
                        </option>
                    </#list>
                </select>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <button class="btn btn-success" type="submit">
            Добавить
        </button>

        <a href="/couriers" class="btn btn-secondary">
            Назад
        </a>

    </form>

</@h.home>
