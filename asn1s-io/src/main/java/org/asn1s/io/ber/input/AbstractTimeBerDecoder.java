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

package org.asn1s.io.ber.input;

import org.asn1s.api.type.Type.Family;
import org.asn1s.api.util.TimeUtils;
import org.asn1s.api.value.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;

abstract class AbstractTimeBerDecoder implements BerDecoder
{
	@Override
	public final Value decode( @NotNull ReaderContext context ) throws IOException
	{
		assert context.getType().getFamily() == getRequiredFamily();
		String timeString = new String( BerDecoderUtils.readString( context.getReader(), context.getLength() ), TimeUtils.CHARSET );
		return context.getValueFactory().timeValue( parseValue( timeString ) );
	}

	@NotNull
	protected abstract Family getRequiredFamily();

	protected abstract Instant parseValue( String timeString );
}