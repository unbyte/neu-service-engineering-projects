$hostsFile = "$( $env:windir )\system32\Drivers\etc\hosts"
$hostsEntries = '127.0.0.1 eurekaa', '127.0.0.1 eurekab', '127.0.0.1 eurekac'

$hostsContent = Get-Content $hostsFile
Foreach ($i in $hostsEntries)
{
    Get-Content $hostsFile | Where-Object { $_ -notmatch $i } | Set-Content $hostsFile
}