package org.toubassi.femtozip.models;

import com.google.common.io.ByteStreams;
import org.toubassi.femtozip.CompressionModel;
import org.toubassi.femtozip.DocumentList;
import org.tukaani.xz.FinishableOutputStream;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.UnsupportedOptionsException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LZMA2DictionaryCompressionModel extends CompressionModel {
    private final LZMA2Options lzma2Options;

    public LZMA2DictionaryCompressionModel() throws UnsupportedOptionsException {
        lzma2Options = new LZMA2Options(LZMA2Options.PRESET_DEFAULT);
    }

    @Override
    public void setDictionary(byte[] dictionary) {
        super.setDictionary(dictionary);
        lzma2Options.setPresetDict(dictionary);
    }

    @Override
    public void encodeLiteral(int aByte, Object context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void encodeSubstring(int offset, int length, Object context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void endEncoding(Object context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void build(DocumentList documents) throws IOException {
    }

    @Override
    public void compress(byte[] data, OutputStream out) throws IOException {
        FinishableOutputStream outputStream = lzma2Options.getOutputStream(new FinishableWrapperOutputStream(out));
        outputStream.write(data);
        outputStream.finish();
    }

    @Override
    public byte[] decompress(byte[] compressedData) {
        try {
            InputStream inputStream = lzma2Options.getInputStream(new ByteArrayInputStream(compressedData));
            return ByteStreams.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
