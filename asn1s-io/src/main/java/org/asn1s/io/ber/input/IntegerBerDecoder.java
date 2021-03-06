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
import org.asn1s.api.value.Value;
import org.asn1s.api.value.x680.IntegerValue;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

final class IntegerBerDecoder implements BerDecoder
{
	@Override
	public Value decode( @NotNull ReaderContext context ) throws IOException
	{
		assert context.getType().getFamily() == Family.INTEGER;
		assert context.getLength() >= 0;
		return readInteger( context.getReader(), context.getLength() );
	}

	static IntegerValue readInteger( AbstractBerReader reader, int length ) throws IOException
	{
		byte[] bytes = new byte[length];
		if( reader.read( bytes ) != length )
			throw new IOException( "Unexpected EOF" );

		return reader.getValueFactory().integer( bytes );
	}
}
