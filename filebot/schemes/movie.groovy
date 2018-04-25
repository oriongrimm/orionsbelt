﻿{fn =~ /2160p|4K|4k|UHD/ ? 'Movies.4K' : fn =~ /3D|3d|3dhsbs|H-SBS|3dhou|H-OU/ ? 'Movies. 3D' : 'Movies'}
/
{collection.replaceFirst(/^(?i)(The)\s(.+)/, /$2, $1/).replaceFirst(/^(?i)(Collection of the)\s(.+)/, /$2 Collection/).replaceAll(/Saga Collection/, "Saga").replaceAll(/[`´‘’ʻ""“”]/, "'").replaceAll(/[:|]/, " - ").replaceAll(/[?]/, "!").replaceAll(/[*\s]+/, " ")}\{norm = {it.upperInitial().lowerTrail().replaceTrailingBrackets().replaceAll(/[`´‘’ʻ""“”]/, "'").replaceAll(/[:|]/, " - ").replaceAll(/[?]/, "!").replaceAll(/[*\s]+/, " ").replaceAll(/\b[IiVvXx]+\b/, { it.upper() }).replaceAll(/\b[0-9](?i:th|nd|rd)\b/, { it.lower() }).replaceFirst(/^(?i)(The)\s(.+)/, /$2, $1/)}; norm(n)}{if (norm(n) != norm(primaryTitle)) ' ('+norm(primaryTitle)+')'}
{fn.contains('3D') || fn.contains('3-D') ? ' '+'3D':""} 
({y}
{' '+any{certification}{info.certification}{imdb.certification}.replaceAll(/^\d+$/, 'PG-$0')})
/
{norm(n)} 
({y}) 
{fn.contains('3D') || fn.contains('3-D') ? ' '+'3D':""}
{' (' + fn.matchAll(/extended|uncensored|remastered|unrated|uncut|directors.cut|special.edition/)*.upperInitial()*.lowerTrail().sort().join(', ').replaceAll(/[._]/, " ") + ')'} 
[{hd}
{def mHDRCol = ["BT.709" : "NO", "BT.2020" : "YES"]; if(bitdepth >= 10 &&  mHDRCol.get(video[0].colourprimaries) == "YES" ) ' HDR ' else '';}
{" $SOURCE"} 
{vf}
{def ChannelString = any{(0.0+audio.'ChannelPositionsString2'*.replaceAll(/Object\sBased\s\/|0.(?=\d.\d)/, '')*.split(' / ')*.collect{ it.split('/')*.toBigDecimal().sum() }*.max().max()).toString()}{channels};
def codecSubVersion = any{audio.any{ a -> call{a.FormatProfile} =~ 'HRA' } ? '.HRA' : null}{audio.any{ a -> call{a.FormatProfile} =~ 'X / MA / Core' } ? '-X' : null}{audio.any{ a -> call{a.FormatProfile} =~ 'MA / Core' } ? ' MA' : null}{audio.any{ a -> call{a.FormatProfile} =~ 'ES Matrix / Core' } ? '-ES' : null}{null};
def codecVersion = any{audio.Codec.join().match(/DTS-HD/)+codecSubVersion+' '+audio.Codec.join().match(/TrueHD/)}{audio.Codec.join().match(/DTS-HD/) && codecSubVersion == '-X' ? 'DTS-X' : null}{audio.Codec.join().match(/DTS-HD/)+codecSubVersion}{audio.Codec.join().match(/TrueHD/)}{audio.Codec.join().match(/DTS/)+codecSubVersion.replaceAll(/null/)}{ac};
(allOf{' '+codecVersion.replaceAll(/null/)}
{if( ((ac == 'AAC'||ac == 'MP3') && (channels != '0.0' || ChannelString != channels) ) || ( (ac == 'AC3'||ac == 'DTS'||ac == 'TrueHD') && (channels != '5.1' || ChannelString != channels) ) ) return any{ChannelString}{channels}}
{aco.match(/Atmos/)}).join(' ')}
{video[0].CodecID =~ /HEVC/ ? ' HEVC' : ''}
{" $GROUP"}]