package com.text.mangofzcotest.core.exceptions

import java.io.IOException

class EmptyBodyException(message: String = "Empty body") : IOException(message)