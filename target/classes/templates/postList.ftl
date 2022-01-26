<#import "parts/common.ftl" as c>

<@c.page>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/posts" class="form-inline">
                <select name="filter" autofocus class="form-control input-lg" id="filter>
                    <option value="">Select please</option>
                    <option>Photo</option>
                    <option>News</option>
                    <option>Information</option>
                </select>
                <button type="submit" class="btn btn-outline-success">Search</button>
            </form>
        </div>
    </div>

    <a class="btn btn-outline-info" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new Post
    </a>

    <div class="collapse show" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" name="postname" class="form-control ${(textError??)?string('is-invalid', '')}"
                           placeholder="Enter the title"/>
                    <div class="form-group mt-3">
                        <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                               name="text" placeholder="Enter post"/>
                    </div>
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <label>Tag</label>
                    <select name="tag" autofocus class="form-control input-lg" id="tag">
                        <option>Photo</option>
                        <option>News</option>
                        <option>Information</option>
                    </select>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-outline-primary">Add Post</button>
                </div>
            </form>
        </div>
    </div>
    <#list posts as post>
        <div class="card border-primary mb-3" style="max-width: 40rem;">
            <div class="card-header">${post.postname}</div>
            <div class="card-body">
                <div class="m-2">
                    <#if post.filename??>
                        <img src="/img/${post.filename}" width="300" height="300">
                    </#if>
                </div>
                <p class="card-title">${post.text}</p>
                <p class="card-text">Tag: ${post.tag}</p>
            </div>
            <div class="card-footer container">
                <p class="card-text">Made by: <a href="/user-messages/${post.author.id}">${post.authorName}</a></p>
                <div class="row" >
                <a class="col align-self-center" href="/posts/${post.id}/like">
                    <font color="red">
                        <#if post.meLiked>
                            <i class="fas fa-heart"></i>
                        <#else>
                            <i class="far fa-heart"></i>
                        </#if>
                    </font>
                        ${post.likes}
                    </a>
                </div>
            </div>
        </div>
    <#else>
        No posts
    </#list>
</@c.page>