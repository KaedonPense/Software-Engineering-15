import { createClient } from '@supabase/supabase-js'

// Create a single supabase client for interacting with your database
const supabase = createClient('https://rjhwvtbdfxvprvcpmqvm.supabase.co', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJqaHd2dGJkZnh2cHJ2Y3BtcXZtIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTQ3MTQ3NDMsImV4cCI6MjAxMDI5MDc0M30.p2LS3j7jcTVwXxWg0O1HkwF_97wK8foSzW7DNjDTfq4')

var playerNo = null;
var name = null;
var equip = null;
var command = null;


if(command == 1)
{
    var { data, error } = await supabase
    .from('players')
    .insert({id : playerNo, code_name: name, equip_id: equip})
}


if(command == 2)
{
    var { error } = await supabase
    .from('players')
    .update({code_name: name})
    .eq('id', playerNo)
}

if(command == 3)
{
    var { error } = await supabase
    .from('players')
    .delete()
    .eq('id', playerNo)
}

var { data, error } = await supabase
.from('players')
.select()

console.log(data)

