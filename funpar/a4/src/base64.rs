#[allow(dead_code)]
use rayon::prelude::*;

pub fn par_encode_base64(bytes: &[u8]) -> String {
    const BASE64_CHARS: &[u8; 64] =
        b"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    let chunks = bytes.par_chunks(3).map(|chunk| {
        // process each chunk of up to 3 bytes
        let byte0 = chunk[0];
        let byte1 = if chunk.len() > 1 { chunk[1] } else { 0 };
        let byte2 = if chunk.len() > 2 { chunk[2] } else { 0 };

        /*
        split into 4 6-bit segments:

        index0 -> first 6 bits of byte0
        index1 -> last 2 bits of byte0 + first 4 bits of byte1
        index2 -> last 4 bits of byte1 + first 2 bits of byte2
        index3 -> last 6 bits of byte2
         */
        let mut v = Vec::with_capacity(4);

        let index0 = (byte0 >> 2) & 0x3F;
        let index1 = ((byte0 << 4) | (byte1 >> 4)) & 0x3F;
        let index2 = ((byte1 << 2) | (byte2 >> 6)) & 0x3F;
        let index3 = byte2 & 0x3F;

        v.push(BASE64_CHARS[index0 as usize]);
        v.push(BASE64_CHARS[index1 as usize]);

        // for if there is more than one chunk, and pad where necessary with =
        if chunk.len() > 1 {
            v.push(BASE64_CHARS[index2 as usize]);
        } else {
            v.push(b'=');
        }

        if chunk.len() > 2 {
            v.push(BASE64_CHARS[index3 as usize]);
        } else {
            v.push(b'=');
        }

        v
    });

    // flatten into a single Vector and return the encoded string
    let encoded: Vec<u8> = chunks.flatten().collect();
    String::from_utf8(encoded).unwrap()
}

#[allow(dead_code)]
pub fn par_decode_base64(code: &str) -> Option<Vec<u8>> {
    // helper to map base64 char to its 6-bit value
    fn base64_map(c: u8) -> Option<u8> {
        match c {
            b'A'..=b'Z' => Some(c - b'A'),
            b'a'..=b'z' => Some(c - b'a' + 26),
            b'0'..=b'9' => Some(c - b'0' + 52),
            b'+' => Some(62),
            b'/' => Some(63),
            _ => None,
        }
    }

    let chunks = code.as_bytes().par_chunks(4).map(|chunk| {
        // process each chunk of up to 4 base64 characters
        let val0 = base64_map(chunk[0]).unwrap_or(0);
        let val1 = base64_map(chunk[1]).unwrap_or(0);
        let val2 = if chunk[2] != b'=' {
            base64_map(chunk[2]).unwrap_or(0)
        } else {
            0
        };
        let val3 = if chunk[3] != b'=' {
            base64_map(chunk[3]).unwrap_or(0)
        } else {
            0
        };

        /*
        reconstruct original bytes from 6-bit segments:

        byte0 -> first 6 bits from val0 + first 2 bits from val1
        byte1 -> last 4 bits from val1 + first 4 bits from val2
        byte2 -> last 2 bits from val2 + all 6 bits from val3
         */
        let mut v = Vec::with_capacity(3);

        let byte0 = (val0 << 2) | (val1 >> 4);
        v.push(byte0);

        // for if there is more than one chunk, and only push non-padded bytes

        if chunk[2] != b'=' {
            let byte1 = ((val1 & 0x0F) << 4) | (val2 >> 2);
            v.push(byte1);
        }

        if chunk[3] != b'=' {
            let byte2 = ((val2 & 0x03) << 6) | val3;
            v.push(byte2);
        }

        Some(v)
    });

    // flatten into a single vector and return the decoded bytes, if decoding was successful
    let decoded_chunks: Option<Vec<Vec<u8>>> = chunks.collect();
    match decoded_chunks {
        Some(vecs) => {
            let decoded: Vec<u8> = vecs.into_iter().flatten().collect();
            Some(decoded)
        }
        None => None,
    }
}

#[cfg(test)]
mod tests {
    use crate::base64::{par_decode_base64, par_encode_base64};

    #[test]
    fn basic_encode() {
        assert_eq!(&"aGVsbG8xNTAxKys9", &par_encode_base64(b"hello1501++="));
        assert_eq!(&"bGlnaHQgd29yaw==", &par_encode_base64(b"light work"));
        assert_eq!(&"IBBnAwJnZw==", &par_encode_base64(b"\x20\x10g\x03\x02gg"));
        // additional tests, used a base64 encoder website to verify
        assert_eq!(&"aWxvdmVmdW5wYXI=", &par_encode_base64(b"ilovefunpar"));
        assert_eq!(
            &"dGhlcmVpc2FsaWdodHRoYXRuZXZlcmdvZXNvdXQ=",
            &par_encode_base64(b"thereisalightthatnevergoesout")
        );
        assert_eq!(
            &"UnVzdCBQcm9ncmFtbWluZw==",
            &par_encode_base64(b"Rust Programming")
        );
    }

    #[test]
    fn basic_decode() {
        assert_eq!(
            Some(b"light work".to_vec()),
            par_decode_base64("bGlnaHQgd29yaw==")
        );
        assert_eq!(
            Some(b"hello1501++=".to_vec()),
            par_decode_base64("aGVsbG8xNTAxKys9")
        );
        assert_eq!(
            Some(b"\x20\x10g\x03\x02gg".to_vec()),
            par_decode_base64("IBBnAwJnZw==")
        );
        // additional tests
        assert_eq!(
            Some(b"ilovefunpar".to_vec()),
            par_decode_base64("aWxvdmVmdW5wYXI=")
        );
        assert_eq!(
            Some(b"thereisalightthatnevergoesout".to_vec()),
            par_decode_base64("dGhlcmVpc2FsaWdodHRoYXRuZXZlcmdvZXNvdXQ=")
        );
        assert_eq!(
            Some(b"Rust Programming".to_vec()),
            par_decode_base64("UnVzdCBQcm9ncmFtbWluZw==")
        );
    }
}
