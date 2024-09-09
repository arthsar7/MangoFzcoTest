package com.text.mangofzcotest.core.exceptions

import java.io.IOException

class UnsuccessfulResponseException(message: String = "Unsuccessful API response") : IOException(message)