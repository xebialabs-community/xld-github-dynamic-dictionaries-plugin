
entries = thisCi.getEntries()
print entries

ci = repositoryService.read(thisCi.id)
print ci
ci.setEntries(entries)
repositoryService.update([ci])
print " -- done -- "
