import { createClient } from '@supabase/supabase-js'

const supabaseUrl = 'https://dhdtwicrgkdqafoudbsh.supabase.co'
const supabaseKey = 'sb_publishable_UUrQq4YbgfSfSWkb-6u9YA_MF_CE4YO'

export const supabase = createClient(supabaseUrl, supabaseKey)