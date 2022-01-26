

<#macro login path isRegisterForm>
    <form action="${path}" method="post" enctype="multipart/form-data">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-6">
                <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="User name"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Password:</label>
                <div class="col-sm-6">
                    <input type="password" name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Retype password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           placeholder="some@some.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Name:</label>
                <div class="col-sm-6">
                    <input type="name" name="name" value="<#if user??>${user.name}</#if>"
                           class="form-control" placeholder="Insert your name" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Surname:</label>
                <div class="col-sm-6">
                    <input type="surname" name="surname" value="<#if user??>${user.surname}</#if>"
                           class="form-control" placeholder="Insert your surname" />
                </div>
            </div>
            <div class="form-group row">
                <div class="input-group">
                    <label class="col-sm-2 col-form-label">Gender:</label>
                    <div class="col-sm-6">
                        <select name="gender" autofocus class="form-control input-lg" id="gender">
                            <option>Male</option>
                            <option>Female</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Phone Number:</label>
                <div class="col-sm-6">
                    <input type="phoneNumber" name="phoneNumber" value="<#if user??>${user.phoneNumber}</#if>"
                           class="form-control" placeholder=" +375000000000" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Profile image:</label>
                <div class="col-sm-6">
                        <div class="custom-file">
                            <input type="file" name="file" id="profileFile">
                            <label class="custom-file-label" for="profileFile">Browse image</label>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">User info:</label>
                <div class="col-sm-6">
                    <input type="info" name="info" value="<#if user??>${user.info}</#if>"
                           class="form-control" placeholder="Tell something about you" />
                </div>
            </div>
            <div class="col-sm-6">
                <div class="g-recaptcha" data-sitekey="6LfTs8kUAAAAAIqOJn90Fm2zVQ9iqqojCZFfuR_m"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-outline-danger" type="submit">Sign Out</button>
    </form>
</#macro>