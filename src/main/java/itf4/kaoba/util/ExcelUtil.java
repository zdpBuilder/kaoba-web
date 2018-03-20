package itf4.kaoba.util;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil<T> {  
	
	private final static String excel2003L =".xls";    //2003- 版本的excel  
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel  
    /** 
     * Excel导入 开始
     */  
    public static  List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception{  
        List<List<Object>> list = null;  
        //创建Excel工作薄  
        Workbook work = getWorkbook(in,fileName);  
        if(null == work){  
            throw new Exception("创建Excel工作薄为空！");  
        }  
        Sheet sheet = null;  
        Row row = null;  
        Cell cell = null;  
        list = new ArrayList<List<Object>>();  
        //遍历Excel中所有的sheet  
        for (int i = 0; i < work.getNumberOfSheets(); i++) {  
            sheet = work.getSheetAt(i);  
            if(sheet==null){continue;}  
            //遍历当前sheet中的所有行  
            //包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部  
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {  
                //读取一行  
                row = sheet.getRow(j);  
                //去掉空行和表头  
                if(row==null||row.getFirstCellNum()==j){continue;}  
                //遍历所有的列  
                List<Object> li = new ArrayList<Object>();  
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {  
                    cell = row.getCell(y);  
                    li.add(getCellValue(cell));  
                }  
                list.add(li);  
            }  
        }  
        return list;  
    }  
    /** 
     * 描述：根据文件后缀，自适应上传文件的版本 
     */  
    public static  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{  
        Workbook wb = null;  
        String fileType = fileName.substring(fileName.lastIndexOf("."));  
        if(excel2003L.equals(fileType)){  
            wb = new HSSFWorkbook(inStr);  //2003-  
        }else if(excel2007U.equals(fileType)){  
            wb = new XSSFWorkbook(inStr);  //2007+  
        }else{  
            throw new Exception("解析的文件格式有误！");  
        }  
        return wb;  
    }  
    /** 
     * 描述：对表格中数值进行格式化 
     */  
    public static  Object getCellValue(Cell cell){  
        Object value = null;  
        DecimalFormat df = new DecimalFormat("0");  //格式化字符类型的数字  
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化  
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字  
        switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_STRING:  
                value = cell.getRichStringCellValue().getString();  
                break;  
            case Cell.CELL_TYPE_NUMERIC:  
                if("General".equals(cell.getCellStyle().getDataFormatString())){  
                    value = df.format(cell.getNumericCellValue());  
                }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){  
                    value = sdf.format(cell.getDateCellValue());  
                }else{  
                    value = df2.format(cell.getNumericCellValue());  
                }  
                break;  
            case Cell.CELL_TYPE_BOOLEAN:  
                value = cell.getBooleanCellValue();  
                break;  
            case Cell.CELL_TYPE_BLANK:  
                value = "";  
                break;  
            default:  
                break;  
        }  
        return value;  
    }  
  /**
   * excel导入结束
   */
    /**
     * excel导出开始
     * @param headers
     * @param dataset
     * @param fileName
     * @param response
     */
    public void exportExcel(String[] headers,Collection<T> dataset, String fileName,HttpServletResponse response) {  
        // 声明一个工作薄  
        XSSFWorkbook workbook = new XSSFWorkbook();  
        // 生成一个表格  
        XSSFSheet sheet = workbook.createSheet(fileName);  
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 20);  
        // 产生表格标题行  
        XSSFRow row = sheet.createRow(0);  
        for (short i = 0; i < headers.length; i++) {  
            XSSFCell cell = row.createCell(i);  
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
        try {  
            // 遍历集合数据，产生数据行  
            Iterator<T> it = dataset.iterator();  
            int index = 0;  
            while (it.hasNext()) {  
                index++;  
                row = sheet.createRow(index);  
                T t = (T) it.next();  
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();  
                for (short i = 0; i < headers.length; i++) {  
                    XSSFCell cell = row.createCell(i);  
                    Field field = fields[i];  
                    String fieldName = field.getName();  
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});  
                    Object value = getMethod.invoke(t, new Object[] {});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    // 其它数据类型都当作字符串简单处理  
                    if(value != null && value != ""){  
                        textValue = value.toString();  
                    }  
                    if (textValue != null) {  
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);  
                        cell.setCellValue(richString);  
                    }  
                }  
            }  
            getExportedFile(workbook, fileName,response);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
    }  
      
    /** 
     *  
     * 方法说明: 指定路径下生成EXCEL文件 
     * @return 
     */  
    public void getExportedFile(XSSFWorkbook workbook, String name,HttpServletResponse response) throws Exception {  
        BufferedOutputStream fos = null;  
        try {  
            String fileName = name + ".xlsx";  
            response.setContentType("application/x-msdownload");  
            response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));  
            fos = new BufferedOutputStream(response.getOutputStream());  
            workbook.write(fos);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fos != null) {  
                fos.close();  
            }  
        }  
    }  
}  
