use std::net::{Shutdown, TcpListener};
use std::thread;
use std::io::Write;
 
const RESPONSE: &'static [u8] = b"HTTP/1.1 200 OK\r
Content-Type: text/html; charset=UTF-8\r
Content-Length: 38\r\n
<html><body>Hello World!</body></html>\r";
 
fn main() {
    let listener = TcpListener::bind("127.0.0.1:80").unwrap();
 
    for stream in listener.incoming() {
        thread::spawn(move || {
            let mut stream = stream.unwrap();
            match stream.write(RESPONSE) {
                Ok(_) => println!("Response sent!"),
                Err(e) => println!("Failed sending response: {}!", e),
            }
            stream.shutdown(Shutdown::Write).unwrap();
        });
    }
}