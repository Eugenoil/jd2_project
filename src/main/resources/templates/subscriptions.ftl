<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>

    <div class="alert alert-success" role="alert">
        ${userChannel.username}
    </div>
    <div class="alert alert-warning" role="alert">
        ${type}
    </div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Username</th>
            <th scope="col">Name</th>
            <th scope="col">Surname</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td class="table-info"><a href="/user-messages/${user.id}">${user.getUsername()}</a></td>
                <td class="table-info">${user.getName()}</td>
                <td class="table-info">${user.getSurname()}</td>
            </tr>
        </#list>
        </tbody>
    </table>

</@c.page>