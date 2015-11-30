package org.toubassi.femtozip;

import com.google.common.io.CharStreams;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LineDocumentList implements DocumentList {
    private final List<byte[]> lines;

    public LineDocumentList(String fileName, Charset charset) throws IOException {
        List<String> stringLines = CharStreams.readLines(new InputStreamReader(new FileInputStream(fileName), charset));
        lines = new ArrayList<byte[]>(stringLines.size());
        for (String stringLine : stringLines) {
            lines.add(stringLine.getBytes(charset));
        }
    }

    @Override
    public int size() {
        return lines.size();
    }

    @Override
    public byte[] get(int i) throws IOException {
        return lines.get(i);
    }
}
