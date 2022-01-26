<#import "parts/common.ftl" as c>

<@c.page>
    <h>List of users</h>

    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Photo</th>
            <th scope="col">E-mail</th>
            <th scope="col">Gender</th>
            <th scope="col">Phone number</th>
            <th scope="col">Role</th>
            <th scope="col">Edit</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <th class="info " scope="col">${user.username}</th>
                <th class="info " scope="col"><#if user.filename??>
                        <img src="/img/${user.filename}" width="80" height="80" alt="${user.username}"></#if></th>
                <th class="info " scope="col">${user.email}</th>
                <th class="info " scope="col">${user.gender}</th>
                <th class="info " scope="col">${user.phoneNumber}</th>
                <th><#list user.roles as role>${role}<#sep>, </#list></th>
                <th><a href="/user/${user.id}">edit</a></th>
            </tr>
        </#list>
        </tbody>
    </table>

    </tbody>
    </table>
</@c.page>