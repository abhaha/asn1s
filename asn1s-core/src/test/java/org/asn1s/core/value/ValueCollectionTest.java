////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010-2017. Lapinin "lastrix" Sergey.                          /
//                                                                             /
// Permission is hereby granted, free of charge, to any person                 /
// obtaining a copy of this software and associated documentation              /
// files (the "Software"), to deal in the Software without                     /
// restriction, including without limitation the rights to use,                /
// copy, modify, merge, publish, distribute, sublicense, and/or                /
// sell copies of the Software, and to permit persons to whom the              /
// Software is furnished to do so, subject to the following                    /
// conditions:                                                                 /
//                                                                             /
// The above copyright notice and this permission notice shall be              /
// included in all copies or substantial portions of the Software.             /
//                                                                             /
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,             /
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES             /
// OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                    /
// NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                /
// HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,                /
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING                /
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                  /
// OR OTHER DEALINGS IN THE SOFTWARE.                                          /
////////////////////////////////////////////////////////////////////////////////

package org.asn1s.core.value;

import org.asn1s.api.Asn1Factory;
import org.asn1s.api.UniversalType;
import org.asn1s.api.module.Module;
import org.asn1s.api.value.DefinedValue;
import org.asn1s.api.value.x680.ValueCollection;
import org.asn1s.core.DefaultAsn1Factory;
import org.junit.Assert;
import org.junit.Test;

public class ValueCollectionTest
{

	private static final double DOUBLE_VALUE_1 = 0.15625d;
	private static final double DOUBLE_VALUE_2 = 5.0E2;

	@Test
	public void testConversionToReal() throws Exception
	{
		Asn1Factory factory = new DefaultAsn1Factory();
		Module module = factory.types().dummyModule();

		ValueCollection collection = factory.values().collection( true );
		collection.addNamed( "mantissa", factory.values().integer( 5 ) );
		collection.addNamed( "base", factory.values().integer( 2 ) );
		collection.addNamed( "exponent", factory.values().integer( -5 ) );

		DefinedValue value = factory.types().define( "a-real", UniversalType.REAL.ref(), collection, null );

		module.validate();

		Assert.assertEquals( "Is not 0.15625", 0, factory.values().real( DOUBLE_VALUE_1 ).compareTo( value.getValue() ) );
	}

	@Test
	public void testConversionToReal2() throws Exception
	{
		Asn1Factory factory = new DefaultAsn1Factory();
		Module module = factory.types().dummyModule();

		ValueCollection collection = factory.values().collection( true );
		collection.addNamed( "mantissa", factory.values().integer( 5 ) );
		collection.addNamed( "base", factory.values().integer( 10 ) );
		collection.addNamed( "exponent", factory.values().integer( 2 ) );

		DefinedValue value = factory.types().define( "a-real", UniversalType.REAL.ref(), collection, null );

		module.validate();

		Assert.assertEquals( "Is not 5E2", 0, factory.values().real( DOUBLE_VALUE_2 ).compareTo( value.getValue() ) );
	}
}
