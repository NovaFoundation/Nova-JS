package nova.js.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class NovaMethodDeclarationWriter extends MethodDeclarationWriter
{
	public abstract NovaMethodDeclaration node();
	
	public StringBuilder writeName(StringBuilder builder)
	{
		super.writeName(builder);
		writeOverload(builder);
		
		return builder;
	}
	
	public StringBuilder writeOverload()
	{
		return writeOverload(new StringBuilder());
	}
	
	public StringBuilder writeOverload(StringBuilder builder)
	{
		if (node().getOverloadID() >= 0)
		{
			String overload = node().getOverloadID() + "";
			
			while (node().getParentClass().containsMethod(node().getName() + overload))
			{
				overload += '_';
			}
			
			return builder.append(overload);
		}
		
		return builder;
	}
	
	public StringBuilder writeAssignedVariable()
	{
		return writeAssignedVariable(new StringBuilder());
	}
	
	public StringBuilder writeAssignedVariable(StringBuilder builder)
	{
		return getWriter(node().getParentClass()).writeName(builder).append(writePrototypeAccess()).append(".").append(writeName());
	}
	
	public StringBuilder writeSuperName()
	{
		return writeSuperName(new StringBuilder());
	}
	
	public StringBuilder writeSuperName(StringBuilder builder)
	{
		return writeName(builder).append("_base").append(getWriter(node().getDeclaringClass()).writeName());
	}
	
	public StringBuilder writePrototypeAssignment(ClassDeclaration clazz)
	{
		return writePrototypeAssignment(new StringBuilder(), clazz);
	}
	
	public StringBuilder writePrototypeAssignment(StringBuilder builder, ClassDeclaration clazz)
	{
		return writePrototypeAssignment(builder, clazz, false);
	}
		
	public StringBuilder writePrototypeAssignment(StringBuilder builder, ClassDeclaration clazz, boolean superCall)
	{
		return getWriter(clazz).writeName(builder).append(writePrototypeAccess()).append('.').append(superCall ? getWriter(node()).writeSuperName() : getWriter(node()).writeName()).append(" = ")
			.append(getWriter(node()).writeAssignedVariable()).append(";\n");
	}
	
	public StringBuilder writePrototypeAccess()
	{
		return writePrototypeAccess(new StringBuilder());
	}
	
	public StringBuilder writePrototypeAccess(StringBuilder builder)
	{
		return builder.append(node().isStatic() ? "" : ".prototype");
	}
}