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

package io.securecodebox.model.results;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.securecodebox.model.findings.Finding;
import io.securecodebox.model.findings.OsiLayer;
import io.securecodebox.model.findings.Reference;
import io.securecodebox.model.findings.Severity;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Rüdiger Heins - iteratec GmbH
 * @since 08.03.18
 */
public class FindingTest {

    private String defaultFindingJson = "{\"id\":\"49bf7fd3-8512-4d73-a28f-608e493cd726\",\"name\":\"BAD_TEST_FINDIG\",\"description\":\"Some coder has tested this!\",\"category\":\"COOL_TEST_STUFF\",\"osiLayer\":\"NOT_APPLICABLE\",\"serverity\":\"HIGH\",\"reference\":{\"id\":\"UNI_CODE_STUFF\",\"source\":\"RISCOOL\"},\"hint\":\"You might wan't to blame Rüdiger!\",\"attributes\":{\"TEST\":\"Kekse\",\"HORRIBLE\":\"Coke\"},\"location\":\"mett.brot.securecodebox.io\"}";
    private String defaultFindingJson2 = "{\"id\":\"49bf7fd3-8512-4d73-a28f-608e493cd126\",\"name\":\"BAD_TEST_FINDIG\",\"description\":\"Some coder has tested this!\",\"category\":\"COOL_TEST_STUFF\",\"osiLayer\":\"NOT_APPLICABLE\",\"serverity\":\"HIGH\",\"reference\":{\"id\":\"UNI_CODE_STUFF\",\"source\":\"RISCOOL\"},\"hint\":\"You might wan't to blame Rüdiger!\",\"location\":\"mett.brot.securecodebox.io\"}";

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSerialize() throws Exception {
        Finding finding = createBasicFinding();

        String result = objectMapper.writeValueAsString(finding);
        assertEquals(defaultFindingJson, result);
        System.out.println("OUT: " + result);
    }

    @Test
    public void testDeSerialize() throws Exception {

        Finding finding = objectMapper.readValue(defaultFindingJson, Finding.class);
        assertEquals(createBasicFinding(), finding);

        Finding finding2 = objectMapper.readValue(defaultFindingJson2, Finding.class);
        assertNotEquals(finding2, finding);
    }

    private Finding createBasicFinding() {
        Finding finding = new Finding();
        finding.setId(UUID.fromString("49bf7fd3-8512-4d73-a28f-608e493cd726"));
        Reference reference = new Reference();
        reference.setId("UNI_CODE_STUFF");
        reference.setSource("RISCOOL");
        finding.setReference(reference);
        finding.setCategory("COOL_TEST_STUFF");
        finding.setName("BAD_TEST_FINDIG");
        finding.setDescription("Some coder has tested this!");
        finding.setHint("You might wan't to blame Rüdiger!");
        finding.setServerity(Severity.HIGH);
        finding.setOsiLayer(OsiLayer.NOT_APPLICABLE);
        finding.setLocation("mett.brot.securecodebox.io");
        finding.addAttribute("TEST", "Kekse");
        finding.addAttribute("HORRIBLE", "Coke");
        return finding;
    }
}
