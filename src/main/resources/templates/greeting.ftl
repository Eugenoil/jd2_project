<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <h1 class="text-center">LinkP</h1>

    <#if name="Guest">
        <h1 class="text-center">Hello, guest!</h1>

        <p class="text-center">Welcome to your social network</p>

        <p class="text-center">Have a nice day!</p>
        <form class="text-center mr-3">
            <a href="/login" class="btn btn-outline-primary" role="button">You can login</a>
        </form>
    </#if>

    <#if user??>
        <#--<img src="/img/${user.filename}" width="290" height="200" alt="${user.username}">-->
        <div class="text-center">
            <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                <div class="card-header">${user.username}</div>
                <div class="card-body">
                    <h5 class="card-title">${user.name}</h5>
                    <h5 class="card-title">${user.surname}</h5>
                    <h5 class="card-title">${user.gender}</h5>
                    <h5 class="card-title">${user.phoneNumber}</h5>
                    <p class="card-text">${user.email}</p>
                </div>
                <div class="card-footer">
                    <p>About me</p>
                    ${user.info}
                </div>
            </div>
        </div>
    </#if>

</@c.page>
