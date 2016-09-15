#
# THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
# FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.
#

from github import Github
import mymodule


__author__ = 'bmoussaud'

print "------"
print "------"
print "------"
print "------"
entries['benoit']='moussaud'
print "------"

from github import Github

# First create a Github instance:
g = Github("bmoussaud", "xxxxxxx")

# Then play with your Github objects:
for repo in g.get_user().get_repos():
    print repo.name
    repo.edit(has_wiki=False)