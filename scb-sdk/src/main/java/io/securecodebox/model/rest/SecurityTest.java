/*
 *
 *  SecureCodeBox (SCB)
 *  Copyright 2015-2018 iteratec GmbH
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package io.securecodebox.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.securecodebox.model.execution.Target;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "A security scan contains the description of a target and the description of the method used to test the target for security defects.")
public class SecurityTest{
    @JsonProperty
    @ApiModelProperty(
        value = "Context references the larger scope the security test. In most cases this is equal to the name of the project.",
        example = "JuiceShop"
    )
    String context;

    @JsonProperty("securitytest")
    @ApiModelProperty(
        value = "Security test to perform on the target.",
        example = "nmap"
    )
    String securityTest;

    @JsonProperty
    @ApiModelProperty("The target of the security test.")
    Target target;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSecurityTest() {
        return securityTest;
    }

    public void setSecurityTest(String securityTest) {
        this.securityTest = securityTest;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    @JsonIgnore
    public String getProcessDefinitionKey(){
        return this.getSecurityTest() + "-process";
    }
}
