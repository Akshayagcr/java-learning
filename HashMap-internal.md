1. When HashMap is created using default no args constructor it creates an array of initial capacity 16 with load factor of 0.75
2. Array which is created is an array of Node object which stores (Key, hash value, Value, next pointer)
3. When we insert an key value pair hashcode() method of object is use to get the hashcode of the object
4. This hashcode is further hashed using an hash function to get the final hashcode
5. Then hashcode % No of buckets is done to calculate index
6. If no key was stored than our new kay value pair is stored.
7. If there is a key value pair already stored it results in collision which is resolved by channing
8. If a collision occurs then equal method of object is used to check equality of keys
9. If keys are equal then value of the stored key is replaced
10. Else We chain the new key value pair as linked list
11. There is a treefying factor of 8
12. i.e. if no of nodes linked in a bucket grows to 8 then the linked list structure is converted to a tree structure
13. Retrieving a value uses same process hashcode of key is calculated to locate the index the equals method is used to check the key for equality
14. if key is equal its value is return or we further traverse linked list or tree.