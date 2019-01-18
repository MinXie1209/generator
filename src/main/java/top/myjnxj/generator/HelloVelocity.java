package top.myjnxj.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import top.myjnxj.generator.entity.TableColumn;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloVelocity {
 public static void main(String[] args) {
 VelocityEngine ve = new VelocityEngine();
 ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
 ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
  
 ve.init();
  
 Template t = ve.getTemplate("hello.vm");
 VelocityContext ctx = new VelocityContext();
  
 ctx.put("name", "velocity");
 ctx.put("date", (new Date()).toString());
  
 List temp = new ArrayList();
  TableColumn tableColumn=new TableColumn();
  tableColumn.setColumnName("jnxj");

 temp.add(tableColumn);
 ctx.put("list", temp);
  
 StringWriter sw = new StringWriter();
  
 t.merge(ctx, sw);
  
 System.out.println(sw.toString());
 }
}