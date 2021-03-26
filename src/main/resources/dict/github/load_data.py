#
# Copyright 2017 XebiaLabs
#
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#

from github import Github

username = ci['username']
password = ci['password']
repository = ci['repository']  # 'xld-petclinic-docker'
branch = ci['branch']
path = ci['path']  # 'dar/config/log4j.properties'

# First create a Github instance:
print ("open a connection to github using the '%s' username" % username)
g = Github(username, password)

print("get '%s' repository" % repository)
repo = g.get_user().get_repo(repository)
print("get content of '%s' in '%s' branch " % (path, branch))
contents = repo.get_contents(path, branch)
# print "decoded_content", contents.decoded_content

entries = {}
for line in contents.decoded_content.split('\n'):
    line = line.rstrip()  # removes trailing whitespace and '\n' chars

    if "=" not in line: continue  # skips blanks and comments w/o =
    if line.startswith("#"): continue  # skips comments which contain =

    k, v = line.split("=", 1)
    entries[k] = v

# print entries
