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
	
	private final static String excel2003L =".xls";    //2003- �汾��excel  
    private final static String excel2007U =".xlsx";   //2007+ �汾��excel  
    /** 
     * Excel���� ��ʼ
     */  
    public static  List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception{  
        List<List<Object>> list = null;  
        //����Excel������  
        Workbook work = getWorkbook(in,fileName);  
        if(null == work){  
            throw new Exception("����Excel������Ϊ�գ�");  
        }  
        Sheet sheet = null;  
        Row row = null;  
        Cell cell = null;  
        list = new ArrayList<List<Object>>();  
        //����Excel�����е�sheet  
        for (int i = 0; i < work.getNumberOfSheets(); i++) {  
            sheet = work.getSheetAt(i);  
            if(sheet==null){continue;}  
            //������ǰsheet�е�������  
            //����ͷ��������ҪС�ڵ������һ����,����Ҳ�����ڳ�ʼֵ����ͷ���������Ա�����ͷ��  
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {  
                //��ȡһ��  
                row = sheet.getRow(j);  
                //ȥ�����кͱ�ͷ  
                if(row==null||row.getFirstCellNum()==j){continue;}  
                //�������е���  
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
     * �����������ļ���׺������Ӧ�ϴ��ļ��İ汾 
     */  
    public static  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{  
        Workbook wb = null;  
        String fileType = fileName.substring(fileName.lastIndexOf("."));  
        if(excel2003L.equals(fileType)){  
            wb = new HSSFWorkbook(inStr);  //2003-  
        }else if(excel2007U.equals(fileType)){  
            wb = new XSSFWorkbook(inStr);  //2007+  
        }else{  
            throw new Exception("�������ļ���ʽ����");  
        }  
        return wb;  
    }  
    /** 
     * �������Ա������ֵ���и�ʽ�� 
     */  
    public static  Object getCellValue(Cell cell){  
        Object value = null;  
        DecimalFormat df = new DecimalFormat("0");  //��ʽ���ַ����͵�����  
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //���ڸ�ʽ��  
        DecimalFormat df2 = new DecimalFormat("0.00");  //��ʽ������  
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
   * excel�������
   */
    /**
     * excel������ʼ
     * @param headers
     * @param dataset
     * @param fileName
     * @param response
     */
    public void exportExcel(String[] headers,Collection<T> dataset, String fileName,HttpServletResponse response) {  
        // ����һ��������  
        XSSFWorkbook workbook = new XSSFWorkbook();  
        // ����һ�����  
        XSSFSheet sheet = workbook.createSheet(fileName);  
        // ���ñ��Ĭ���п��Ϊ15���ֽ�  
        sheet.setDefaultColumnWidth((short) 20);  
        // ������������  
        XSSFRow row = sheet.createRow(0);  
        for (short i = 0; i < headers.length; i++) {  
            XSSFCell cell = row.createCell(i);  
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
        try {  
            // �����������ݣ�����������  
            Iterator<T> it = dataset.iterator();  
            int index = 0;  
            while (it.hasNext()) {  
                index++;  
                row = sheet.createRow(index);  
                T t = (T) it.next();  
                // ���÷��䣬����javabean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ  
                Field[] fields = t.getClass().getDeclaredFields();  
                for (short i = 0; i < headers.length; i++) {  
                    XSSFCell cell = row.createCell(i);  
                    Field field = fields[i];  
                    String fieldName = field.getName();  
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});  
                    Object value = getMethod.invoke(t, new Object[] {});  
                    // �ж�ֵ�����ͺ����ǿ������ת��  
                    String textValue = null;  
                    // �����������Ͷ������ַ����򵥴���  
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
     * ����˵��: ָ��·��������EXCEL�ļ� 
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
