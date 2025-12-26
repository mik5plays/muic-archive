for i in {1..100}; do
  (echo "BAD REQUEST\r\n\r\n" | nc localhost 8080 &)
done
