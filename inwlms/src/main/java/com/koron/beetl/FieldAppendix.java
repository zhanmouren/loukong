package com.koron.beetl;

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;

public class FieldAppendix implements Function {

	@Override
	public Object call(Object[] arg, Context ctx) {
		StringBuffer sb = new StringBuffer();
		if (ctx.getGlobal("flag") == null || !(ctx.getGlobal("flag") instanceof Map))
			return "";
		@SuppressWarnings("unchecked")
		HashMap<String, String> flag = (HashMap<String, String>) ctx.getGlobal("flag");
		if (arg != null && arg.length > 0 && arg[0].equals("onload")) {
			if (flag.get("UD_DATE") != null && flag.get("UD_DATE").equals("true")) {
				sb.append("jQuery(\".datetimepicker\").datetimepicker({format:\"Y-m-d H:i\",lang:'ch'});").append('\n');
				sb.append("jQuery(\".datepicker\").datetimepicker({timepicker:false,format:\"Y-m-d\",lang:'ch'});").append('\n');
				sb.append("jQuery(\".timepicker\").datetimepicker({datepicker:false,format:\"H:i\",lang:'ch'});").append('\n');
			}
			if (flag.get("UD_HAS_ASSIST") != null && flag.get("UD_HAS_ASSIST").equals("true")) {
				sb.append("jQuery(\"._assist\").change(function(){_assist_function(this);});").append('\n');
			}
		} else {
			if (flag.get("UD_DATE") != null && flag.get("UD_DATE").equals("true")) {
				sb.append("<link href=\"" + ctx.getGlobal("ctxPath") + "/addone/datetimepicker/datetimepicker.css\" rel=\"stylesheet\">").append("\n");
				sb.append("<script src=\"" + ctx.getGlobal("ctxPath") + "/addone/datetimepicker/datetimepicker.js\"></script>").append("\n");
			}
			if (flag.get("UD_PIC") != null && flag.get("UD_PIC").equals("true")) {
				sb.append("<script src=\"" + ctx.getGlobal("ctxPath") + "/js/upload.js\"></script>").append("\n");
			}
			if (flag.get("FORM_APPENDIX") != null) {
				String key = flag.get("FORM_APPENDIX");
				String[] keys = key.split(",");
				for (String string : keys) {
					if (!string.trim().isEmpty()) {
						if (string.trim().toLowerCase().endsWith(".css"))
							sb.append("<link href=\"" + ctx.getGlobal("ctxPath") + "/" + string.trim() + "\" rel=\"stylesheet\">");
						else
							sb.append("<script src=\"" + ctx.getGlobal("ctxPath") + "/" + string.trim() + "\"></script>").append("\n");
					}
				}
			}
		}
		return sb.toString();
	}
}
