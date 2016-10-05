package nova.js.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ScopeWriter extends NodeWriter
{
	public abstract Scope node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return write(builder, true);
	}
	
	public StringBuilder write(StringBuilder builder, boolean braces)
	{
		return write(builder, braces, true);
	}
	
	public StringBuilder write(StringBuilder builder, boolean braces, boolean newLine)
	{
		if (node().getNumChildren() <= 1)
		{
			if (node().getParent() instanceof Loop)
			{
				// Insert the semicolon before the new line.
				return builder.insert(builder.length() - 1, ";");
			}
		}
		
		if (braces)
		{
			builder.append('{').append('\n');
		}
		
		node().forEachChild(child -> {
			getWriter(child).write(builder);
		});
		
		if (braces)
		{
			builder.append('}');
		}
		if (newLine)
		{
			builder.append('\n');
		}
		
		return builder;
	}
}