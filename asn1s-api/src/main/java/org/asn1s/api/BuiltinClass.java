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

package org.asn1s.api;


import org.asn1s.api.type.Type;
import org.asn1s.api.type.TypeNameRef;
import org.jetbrains.annotations.NotNull;

public enum BuiltinClass
{
	TYPE_IDENTIFIER( "TYPE-IDENTIFIER" );

	private final String typeName;
	private final Ref<Type> ref;

	BuiltinClass( String typeName )
	{
		this.typeName = typeName;
		ref = new TypeNameRef( typeName, null );
	}

	@NotNull
	public String typeName()
	{
		return typeName;
	}

	public Ref<Type> ref()
	{
		return ref;
	}
}
