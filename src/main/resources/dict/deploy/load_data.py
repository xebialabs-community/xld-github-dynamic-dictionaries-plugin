#
# Copyright 2020 XEBIALABS
#
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#

from http.http_connection import HttpConnection
from http.http_request import HttpRequest
import json

headers = {'Content-Type': 'application/json', 'Accept': 'application/json'}
url = "{url}/{context}".format(**ci)
print(url)
http_connection = HttpConnection(url, ci['username'], ci['password'])
request = HttpRequest(http_connection, ci['username'], ci['password'])
r = request.get("/repository/ci/{ci}".format(**ci), headers=headers)
if r.isSuccessful():
    results = json.loads(r.response)['entries']
    for key in results:
        dynamic_entries[key] = results[key]
else:
    r.errorDump()
    raise Exception("{0} {1}".format(r.status, r.response))

# print dynamic_entries
