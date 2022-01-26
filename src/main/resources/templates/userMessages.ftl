<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <div class="card-group">
    <div class="col-sm-6">
    <div class="card border-success mb-3">
        <div class="card-header">${userName} ${userSurname}</div>
        <div class="card-header">${userChannel.username}</div>
        <div class="card-body text-success">
            <#--<h5 class="card-title"><img src="/img/${userPhoto}" width="250" height="200" alt="${userPhoto}"></h5>-->
            <p class="card-text"></p>
        </div>
    </div>
    </div>
    <div class="col-sm-6">
    <div class="card border-primary mb-3">
            <div class="card-body">
                <h5 class="card-title">About me</h5>
                <p class="card-text">${info}</p>
            </div>
        </div>
    </div>
    </div>
    <div class="col">
    <#if !isCurrentUser>
        <#if isSubscriber>
            <a class="btn btn-outline-danger" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a>
        <#else>
            <a class="btn btn-outline-success" href="/user/subscribe/${userChannel.id}">Subscribe</a>
        </#if>
    </#if>
    </div>
    <div class="container my-3">
        <div class="row">
            <div class="col">
                <div class="card border-info mb-3">
                    <div class="card-body">
                        <div class="card-title">Friends</div>
                        <h3 class="card-text">
                            <a href="/user/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card border-info mb-3">
                    <div class="card-body">
                        <div class="card-title">Followers</div>
                        <h3 class="card-text">
                            <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col">
    <#if isCurrentUser>
        <#include "parts/messageEdit.ftl" />
    </#if>
    </div>
    <div class="col">
    <#include "parts/messageList.ftl" />
    </div>
</@c.page>