package mts;

import java.io.IOException;
import java.util.Arrays;

import crc32.Crc32Mpeg2;


import flavor.BuffBitstream;
import flavor.FlIOException;

public class TestPatDecode {
	public static void main(String[] args) throws IOException {
		String filename = "C:/Users/res/Downloads/media_w2099157005_0.ts";
		BuffBitstream stream = new BuffBitstream(filename, BuffBitstream.BS_INPUT );
		stream.getbits(8);
		stream.getbits(8);
		stream.getbits(8);
		stream.getbits(8);
		stream.getbits(8);
		;
		flavor.generated.PAT pat_parser = new flavor.generated.PAT();
		int ret = pat_parser.get(stream);
		System.out.println(ret);
		
		// 根据  pat 计算 crc32
		byte[] bytes = new byte[]{0x00, (byte)0xB0, 0x0D, 0x00, 0x01, (byte)0xC1, 0x00, 0x00, 0x00, 0x01, (byte)0xEF, (byte)0xFF };
		Crc32Mpeg2 crc32 = new Crc32Mpeg2();
		crc32.update(bytes);
		byte[] codes = crc32.getByteArray();
		System.out.println(Arrays.toString(codes));
		
	}
}
