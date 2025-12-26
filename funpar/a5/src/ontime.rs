use std::io;

struct FlightRecord {
    unique_carrier: String,
    actual_elapsed_time: i32,
    arrival_delay: i32,
}

#[allow(dead_code)]
pub fn par_split<'a>(st_buf: &'a str, split_char: char) -> Vec<&'a str> {
    use rayon::iter::*;

    let bytes = st_buf.as_bytes();
    let n = bytes.len();

    if n == 0 {
        return vec![""];
    }

    // mark start of slices
    let flags: Vec<i32> = (0..n)
        .into_par_iter()
        .map(|i| {
            if i == 0 {
                1
            } else if st_buf.as_bytes()[i - 1] == split_char as u8 {
                1
            } else {
                0
            }
        })
        .collect();

    let indexes: Vec<i32> = (0..n).map(|i| i as i32).collect();

    // filter from last task
    let start_slices = crate::filter::par_filter(&indexes, |i| flags[i as usize] == 1);

    let mut result = Vec::with_capacity(start_slices.len());

    for k in 0..start_slices.len() {
        let start = start_slices[k] as usize;
        let end = if k + 1 < start_slices.len() {
            let next = start_slices[k + 1] as usize;
            if next == 0 {
                0
            } else {
                next - 1
            }
        } else {
            n - 1
        };

        if start > end {
            result.push("");
        } else {
            result.push(&st_buf[start..end]);
        }
    }

    result
}

#[allow(dead_code)]
fn parse_line(line: &str) -> Option<FlightRecord> {
    let split = par_split(line, ',');

    let unique_carrier = split.get(8)?.to_string();
    let actual_elapsed_time = split
        .get(11)
        .and_then(|s| s.parse::<i32>().ok()) // None if other error
        .unwrap_or(0); // 0 if parsing fail

    let arrival_delay = split
        .get(14)
        .and_then(|s| s.parse::<i32>().ok()) // None if other error
        .unwrap_or(0); // 0 if parsing fail

    Some(FlightRecord {
        unique_carrier,
        actual_elapsed_time,
        arrival_delay,
    })
}

#[allow(dead_code)]
/*
This code kinda sucks for larger .csv files because of parallel overheads and such,
it works fine for ~100k lines but I had to heavily sanitize (as in, convert manually to UTF-8)
because the encoding was messed up on the original 2008.csv (probably because its like 7 million lines)
*/
pub fn ontime_rank(filename: &str) -> Result<Vec<(String, f64)>, io::Error> {
    use chashmap::CHashMap;
    use rayon::prelude::*;
    use std::fs;

    let file_contents = fs::read_to_string(filename)?;

    let lines: Vec<&str> = par_split(&file_contents, '\n');

    // (unique_carrier, (on_time_count, total_count))
    let records = CHashMap::<String, (usize, usize)>::new();

    lines.par_iter().for_each(|line| {
        if let Some(record) = parse_line(line) {
            let on_time = record.arrival_delay <= 0;
            let carrier = record.unique_carrier;

            records.upsert(
                carrier,
                || if on_time { (1, 1) } else { (0, 1) },
                |v| {
                    if on_time {
                        v.0 += 1;
                    }
                    v.1 += 1;
                },
            );
        }
    });

    let mut result: Vec<(String, f64)> = records
        .into_iter()
        .map(|(carrier, (on_time_count, total_count))| {
            let rate = (on_time_count as f64) * 100.0 / (total_count as f64); // calculate total in percent (out of 100.0)
            (carrier, rate)
        })
        .collect();

    // parallel sorting using rayon built-in sort
    result.par_sort_by(|a, b| b.1.partial_cmp(&a.1).unwrap());

    Ok(result)
}
