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

import org.asn1s.api.UniversalType;
import org.asn1s.api.encoding.tag.Tag;
import org.asn1s.api.encoding.tag.TagClass;
import org.asn1s.api.type.CollectionType;
import org.asn1s.api.type.Type;
import org.asn1s.api.type.Type.Family;
import org.asn1s.api.value.x680.NamedValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

final class SequenceBerEncoder extends AbstractCollectionBerEncoder
{
	public static final Tag TAG = new Tag( TagClass.UNIVERSAL, true, UniversalType.SEQUENCE.tagNumber() );
	private static final Tag TAG_INSTANCE_OF = new Tag( TagClass.UNIVERSAL, true, UniversalType.INSTANCE_OF.tagNumber() );

	@NotNull
	@Override
	protected Tag getTag( @NotNull Type type )
	{
		return ( (CollectionType)type ).isInstanceOf() ? TAG_INSTANCE_OF : TAG;
	}

	@NotNull
	@Override
	protected Collection<NamedValue> getValues( @NotNull WriterContext context )
	{
		return context.getValue().toValueCollection().asNamedValueList();
	}

	@NotNull
	@Override
	protected Family getRequiredFamily()
	{
		return Family.SEQUENCE;
	}
}
