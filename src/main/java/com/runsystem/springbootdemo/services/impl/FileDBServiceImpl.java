package com.runsystem.springbootdemo.services.impl;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.runsystem.springbootdemo.common.FileType;
import com.runsystem.springbootdemo.elasticsearch.repositories.FileDBElasticsearchRepository;
import com.runsystem.springbootdemo.models.FileDB;
import com.runsystem.springbootdemo.models.FileDBElasticsearch;
import com.runsystem.springbootdemo.repositories.FileDBRepository;
import com.runsystem.springbootdemo.services.FileDBService;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FileDBServiceImpl implements FileDBService {
    /** create bean FileDBRepository */
    @Autowired
    FileDBRepository fileDBRepository;

    /** create bean FileDBElasticsearchRepository */
    @Autowired
    FileDBElasticsearchRepository fileDBElasticsearchRepository;

    /**
     * This function to get value from cell
     * @param cell Cell type from excel
     * @return Object value
     */
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }

    @Override
    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        final FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        String tempFilePath = null;
        StringBuilder content = new StringBuilder();

        if (!(Objects.equals(file.getContentType(), FileType.DOCUMENT) || Objects.equals(file.getContentType(), FileType.MSWORD) || Objects.equals(
                file.getContentType(), FileType.SHEET) || Objects.equals(file.getContentType(), FileType.MS_EXCEL) || Objects.equals(
                file.getContentType(), FileType.PDF))) {
            return null;
        }

        // get path of file
        try {
            File tempFile = File.createTempFile(file.getOriginalFilename(), "");
            file.transferTo(tempFile);
            tempFilePath = tempFile.getAbsolutePath();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // read word file
        if ((Objects.equals(file.getContentType(), FileType.DOCUMENT) || Objects.equals(file.getContentType(), FileType.MSWORD))) {
            try {
                InputStream fileword = Files.newInputStream(Paths.get(tempFilePath));
                XWPFDocument document = new XWPFDocument(OPCPackage.open(fileword));
                XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
                content = new StringBuilder(wordExtractor.getText());
                wordExtractor.close();
                document.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // read pdf file
        if ((Objects.equals(file.getContentType(), FileType.PDF))) {
            try {
                //create PdfReader witj tempFilePath
                PdfReader filePDF = new PdfReader(tempFilePath);
                //Sử dụng PdfTextExtractor để đọc toàn bộ text ở trang 100
                for (int i = 1; i <= filePDF.getNumberOfPages(); i++) {
                    content.append(PdfTextExtractor.getTextFromPage(filePDF, i));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //read Excel file
        if (Objects.equals(file.getContentType(), FileType.SHEET) || Objects.equals(file.getContentType(), FileType.MS_EXCEL)) {
            try {
                InputStream fileExcel = Files.newInputStream(new File(tempFilePath).toPath());
                // create workbook for file xlsx
                XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);
                // get first worksheet in workbook
                XSSFSheet sheet = workbook.getSheetAt(0);
                // iterate all row in sheet
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    // For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        Object cellValue = getCellValue(cell);
                        if (cellValue == null || cellValue.toString().isEmpty()) {
                            content = new StringBuilder(content + "/");
                            continue;
                        }
                        content = new StringBuilder(content + "/" + cellValue.toString());
                    }
                    content = new StringBuilder(content + "\n");
                }
                fileExcel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileDB result = fileDBRepository.save(fileDB);
        FileDBElasticsearch fileDBElasticsearch = new FileDBElasticsearch();
        fileDBElasticsearch.setId(result.getId());
        fileDBElasticsearch.setContent(content.toString());
        fileDBElasticsearchRepository.save(fileDBElasticsearch);
        return result;
    }

    @Override
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    @Override
    public Optional<FileDB> getById(Long id) {
        return fileDBRepository.findById(id);
    }

    @Override
    public Stream<FileDB> getFilesByWord(String word) {
        List<FileDBElasticsearch> fileDBElasticsearchList = fileDBElasticsearchRepository.findFileDBElasticsearchByContentContainsIgnoreCase(word);
        List<FileDB> fileDBList = new ArrayList<FileDB>();
        FileDB fileDB;
        for (FileDBElasticsearch el : fileDBElasticsearchList) {
            fileDB = fileDBRepository.findFileDBById(el.getId());
            fileDBList.add(fileDB);
        }
        return fileDBList.stream();
    }
}
