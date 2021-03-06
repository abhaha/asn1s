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

package org.asn1s.core.type;


import org.asn1s.api.Ref;
import org.asn1s.api.Scope;
import org.asn1s.api.Template;
import org.asn1s.api.exception.ResolutionException;
import org.asn1s.api.exception.ValidationException;
import org.asn1s.api.module.Module;
import org.asn1s.api.type.AbstractNestingType;
import org.asn1s.api.type.DefinedType;
import org.asn1s.api.type.Type;
import org.asn1s.api.type.TypeNameRef;
import org.asn1s.api.util.RefUtils;
import org.asn1s.core.CoreUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefinedTypeImpl extends AbstractNestingType implements DefinedType
{
	public DefinedTypeImpl( @NotNull Module module, @NotNull String name, @Nullable Ref<Type> reference )
	{
		super( reference );
		RefUtils.assertTypeRef( name );
		this.module = module;
		this.name = name;

		if( CoreUtils.CORE_MODULE_NAME.equals( module.getModuleName() ) )
			setNamespace( null );
		else
			setNamespace( module.getModuleName() + ':' );
	}

	private Module module;
	private final String name;
	private Template template;

	@Nullable
	@Override
	public Template getTemplate()
	{
		return template;
	}

	public void setTemplate( @Nullable Template template )
	{
		this.template = template;
	}

	@NotNull
	@Override
	public Scope createScope()
	{
		return module.createScope().typedScope( this );
	}

	@NotNull
	@Override
	public Scope getScope( @NotNull Scope parentScope )
	{
		return template == null
				? parentScope.typedScope( this )
				: parentScope.typedScope( this ).templateScope( template, this, getModule() );
	}

	@Override
	public Module getModule()
	{
		return module;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	protected void onDispose()
	{
		module = null;
	}

	@Override
	protected void onValidate( @NotNull Scope scope ) throws ResolutionException, ValidationException
	{
		if( isAbstract() && !template.isInstance() )
			throw new ValidationException( "Unable to instantiate template types" );

		super.onValidate( template == null ? createScope() : getScope( scope ) );
	}

	@NotNull
	@Override
	public Type copy()
	{
		DefinedTypeImpl type = new DefinedTypeImpl( module, getName(), cloneSibling() );
		type.setTemplate( template );
		return type;
	}

	@Override
	public Ref<Type> toRef()
	{
		return new TypeNameRef( getName(), module.getModuleName() );
	}

	@Override
	public String toString()
	{
		return getName();
	}

	@Override
	protected String getSiblingNamespace()
	{
		return getFullyQualifiedName() + '.';
	}
}
