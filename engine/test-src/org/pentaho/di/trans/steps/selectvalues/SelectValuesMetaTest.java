/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.steps.selectvalues;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.steps.loadsave.LoadSaveTester;
import org.pentaho.di.trans.steps.loadsave.validator.ArrayLoadSaveValidator;
import org.pentaho.di.trans.steps.loadsave.validator.FieldLoadSaveValidator;
import org.pentaho.di.trans.steps.selectvalues.SelectValuesMeta.SelectField;

public class SelectValuesMetaTest {

  @Test
  public void loadSaveTest() throws KettleException {
    List<String> attributes = Arrays.asList( "selectFields", "deleteName" );

    SelectField selectField = new SelectField();
    selectField.setName( "TEST_NAME" );
    selectField.setRename( "TEST_RENAME" );
    selectField.setLength( 2 );
    selectField.setPrecision( 2 );

    Map<String, FieldLoadSaveValidator<?>> fieldLoadSaveValidatorTypeMap =
        new HashMap<String, FieldLoadSaveValidator<?>>();
    fieldLoadSaveValidatorTypeMap.put( SelectField[].class.getCanonicalName(), new ArrayLoadSaveValidator<SelectField>(
        new SelectFieldLoadSaveValidator( selectField ), 2 ) );

    LoadSaveTester tester =
        new LoadSaveTester( SelectValuesMeta.class, attributes, new HashMap<String, String>(),
            new HashMap<String, String>(), new HashMap<String, FieldLoadSaveValidator<?>>(),
            fieldLoadSaveValidatorTypeMap );

    tester.testRepoRoundTrip();
    tester.testXmlRoundTrip();
  }

  public static class SelectFieldLoadSaveValidator implements FieldLoadSaveValidator<SelectField> {

    private final SelectField defaultValue;

    public SelectFieldLoadSaveValidator( SelectField defaultValue ) {
      this.defaultValue = defaultValue;
    }

    @Override
    public SelectField getTestObject() {
      return defaultValue;
    }

    @Override
    public boolean validateTestObject( SelectField testObject, Object actual ) {
      return EqualsBuilder.reflectionEquals( testObject, actual );
    }
  }
}
