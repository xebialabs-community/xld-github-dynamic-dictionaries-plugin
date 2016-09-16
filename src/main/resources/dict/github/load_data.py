#
# THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
# FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.
#
from github import Github

username = ci['username']
password = ci['password']
repository = ci['repository']  # 'xld-petclinic-docker'
branch = ci['branch']
path = ci['path']  # 'dar/config/log4j.properties'


# First create a Github instance:
print "open a connection to github using the '%s' username" % username
g = Github(username, password)
print "get '%s' repository" % repository
repo = g.get_user().get_repo(repository)
print "get content of '%s' in '%s' branch " % (path, branch)
contents = repo.get_contents(path, branch)
# print "decoded_content", contents.decoded_content

for line in contents.decoded_content.split('\n'):
    line = line.rstrip()  # removes trailing whitespace and '\n' chars

    if "=" not in line: continue  # skips blanks and comments w/o =
    if line.startswith("#"): continue  # skips comments which contain =

    k, v = line.split("=", 1)
    entries[k] = v

# print entries
