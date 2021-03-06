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
import org.asn1s.api.constraint.ConstraintUtils;
import org.asn1s.api.encoding.tag.Tag;
import org.asn1s.api.encoding.tag.TagClass;
import org.asn1s.api.type.Type.Family;
import org.asn1s.api.value.ByteArrayValue;
import org.asn1s.api.value.Value.Kind;
import org.asn1s.io.ber.BerRules;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

final class BitStringBerEncoder implements BerEncoder
{
	private static final Tag TAG = new Tag( TagClass.UNIVERSAL, false, UniversalType.BIT_STRING.tagNumber() );

	@Override
	public void encode( @NotNull WriterContext context ) throws IOException
	{
		assert context.getType().getFamily() == Family.BIT_STRING;
		assert context.getValue().getKind() == Kind.BYTE_ARRAY;
		ByteArrayValue arrayValue = context.getValue().toByteArrayValue();
		boolean hasNamedValues = !context.getType().getNamedValues().isEmpty();

		byte[] bytes = arrayValue.asByteArray();
		int usedBits = arrayValue.getUsedBits();
		int emptyBits = bytes.length * 8 - usedBits;
		if( context.getRules() == BerRules.DER )
		{
			boolean hasSizeConstraint = Boolean.TRUE.equals( context.getScope().getScopeOption( ConstraintUtils.OPTION_HAS_SIZE_CONSTRAINT ) );

			if( !hasSizeConstraint && hasNamedValues )
			{
				if( hasEmptyBytesAtEnd( bytes ) )
					bytes = removeZerosFromEnd( bytes );

				// optimize amount of zero bits at end of last byte
				if( bytes.length > 0 )
					emptyBits = getTrailingZerosCount( bytes[bytes.length - 1] );
			}
		}

		int length = bytes.length == 0 ? 1 : 1 + bytes.length;
		context.writeHeader( TAG, length );

		if( length > 1 )
		{
			context.write( emptyBits );
			context.write( bytes );
		}
		else
			context.write( 0 );
	}

	private static int getTrailingZerosCount( byte last )
	{
		int emptyBits;
		if( last == 0 )
			emptyBits = 1;
		else
		{
			emptyBits = 0;
			byte mask = 0x01;
			for( int i = 0; i < 7; i++ )
			{
				if( ( last & mask ) != 0 )
					break;

				mask <<= 1;
				emptyBits++;
			}
		}
		return emptyBits;
	}

	private static boolean hasEmptyBytesAtEnd( byte[] bytes )
	{
		return bytes != null && bytes.length > 1 && bytes[bytes.length - 1] == 0;
	}

	private static byte[] removeZerosFromEnd( byte[] bytes )
	{
		int emptyCount = 0;
		for( int i = bytes.length - 1; i >= 0; i-- )
		{
			if( bytes[i] != 0 )
				break;

			emptyCount++;
		}

		byte[] result = new byte[bytes.length - emptyCount];
		System.arraycopy( bytes, 0, result, 0, result.length );
		return result;
	}
}
