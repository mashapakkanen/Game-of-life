class wrongFile(name: String, type: String) :
    Exception("File $name isn't $type file") {}

class noFile(fileName: String, filePath: String) :
    Exception("Can't find file $fileName in path $filePath")
