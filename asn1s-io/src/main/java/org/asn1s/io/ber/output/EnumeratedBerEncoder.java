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

package org.asn1s.io.ber.output;

import org.asn1s.api.Scope;
import org.asn1s.api.UniversalType;
import org.asn1s.api.encoding.tag.Tag;
import org.asn1s.api.encoding.tag.TagClass;
import org.asn1s.api.exception.Asn1Exception;
import org.asn1s.api.type.Type;
import org.asn1s.api.type.Type.Family;
import org.asn1s.api.value.Value;
import org.asn1s.api.value.Value.Kind;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

final class EnumeratedBerEncoder implements BerEncoder
{
	private static final Tag TAG = new Tag( TagClass.Universal, false, UniversalType.Enumerated.tagNumber() );

	@Override
	public void encode( @NotNull BerWriter os, @NotNull Scope scope, @NotNull Type type, @NotNull Value value, boolean writeHeader ) throws IOException, Asn1Exception
	{
		assert type.getFamily() == Family.Enumerated;
		assert value.getKind() == Kind.Name && value.toNamedValue().getReferenceKind() == Kind.Integer;
		IntegerBerEncoder.writeLong( os, value.toIntegerValue().asLong(), TAG, writeHeader );
	}
}
