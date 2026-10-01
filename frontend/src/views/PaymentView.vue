<template>
  <div class="page payment-page">
    <div class="container payment-grid">

      <section class="page-head">
        <router-link to="/pass" class="back-link">← Back to My Pass</router-link>
        <span class="section-eyebrow">Payment</span>
        <h1>Buy or renew your pass</h1>
        <p class="page-sub">
          Choose a pass type and preferred route. Payment is simulated for now.
        </p>
      </section>

      <div class="payment-layout">

        <section class="glass form-card">
          <h2 class="card-title">Pass details</h2>

          <div class="field">
            <label for="passType">Pass type</label>
            <select id="passType" v-model="form.passType">
              <option value="monthly">Monthly pass – R 350</option>
              <option value="semester">Semester pass – R 1 200</option>
              <option value="yearly">Yearly pass – R 2 000</option>
            </select>
          </div>

          <div class="field">
            <label for="route">Preferred route</label>
            <select id="route" v-model="form.routeId">
              <option value="1">G1 – Khayelitsha → CPUT Bellville</option>
              <option value="2">G2 – Mitchells Plain → CPUT Cape Town</option>
              <option value="3">G3 – Kraaifontein → CPUT Bellville</option>
            </select>
          </div>

          <div class="field">
            <label for="cardName">Name on card</label>
            <input
                id="cardName"
                v-model="form.cardName"
                type="text"
                placeholder="As on your card"
                autocomplete="cc-name"
            />
          </div>

          <div class="field">
            <label for="cardNumber">Card number</label>
            <input
                id="cardNumber"
                v-model="form.cardNumber"
                type="text"
                maxlength="19"
                placeholder="xxxx xxxx xxxx xxxx"
                inputmode="numeric"
                autocomplete="cc-number"
                @input="formatCardNumber"
            />
          </div>

          <div class="field-row">
            <div class="field">
              <label for="expiry">Expiry</label>
              <input
                  id="expiry"
                  v-model="form.expiry"
                  type="text"
                  maxlength="5"
                  placeholder="MM/YY"
                  inputmode="numeric"
                  autocomplete="cc-exp"
              />
            </div>
            <div class="field">
              <label for="cvv">CVV</label>
              <input
                  id="cvv"
                  v-model="form.cvv"
                  type="password"
                  maxlength="4"
                  placeholder="•••"
                  inputmode="numeric"
                  autocomplete="cc-csc"
              />
            </div>
          </div>

          <div class="status-box" :class="statusClass" v-if="statusText">
            {{ statusText }}
          </div>

          <button class="btn btn-primary" :disabled="paying" @click="pay">
            {{ paying ? 'Processing…' : `Pay R ${selectedPrice}` }}
          </button>
        </section>

        <section class="glass summary-card">
          <h2 class="card-title">Order summary</h2>

          <div class="summary-row">
            <span>Pass type</span>
            <strong>{{ passTypeLabel }}</strong>
          </div>
          <div class="summary-row">
            <span>Route</span>
            <strong>{{ routeLabel }}</strong>
          </div>
          <div class="summary-row">
            <span>Valid for</span>
            <strong>{{ validityLabel }}</strong>
          </div>
          <div class="summary-row total">
            <span>Total</span>
            <strong>R {{ selectedPrice }}</strong>
          </div>

          <p class="summary-note">
            Demo payment only — no real card is charged. A row is written to your
            <code>passes</code> table on success and a confirmation email is sent.
          </p>
        </section>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { supabase } from '../supabaseClient'

const router = useRouter()

const form = reactive({
  passType: 'monthly',
  routeId: '1',
  cardName: '',
  cardNumber: '',
  expiry: '',
  cvv: ''
})

const paying = ref(false)
const statusText = ref('')
const statusClass = ref('')

const prices = {
  monthly: 350,
  semester: 1200,
  yearly: 2000
}

const validityMonths = {
  monthly: 1,
  semester: 6,
  yearly: 12
}

const passTypeMap = {
  monthly: 'Monthly',
  semester: 'Semester',
  yearly: 'Yearly'
}

const selectedPrice = computed(() => prices[form.passType] || 0)
const passTypeLabel = computed(() => passTypeMap[form.passType] || '—')

const routeLabel = computed(() => {
  const map = {
    '1': 'G1 – Khayelitsha → CPUT Bellville',
    '2': 'G2 – Mitchells Plain → CPUT Cape Town',
    '3': 'G3 – Kraaifontein → CPUT Bellville'
  }
  return map[form.routeId] || '—'
})

const validityLabel = computed(() => {
  const m = validityMonths[form.passType]
  return m === 1 ? '1 month' : `${m} months`
})

function formatCardNumber() {
  const digits = form.cardNumber.replace(/\D/g, '').slice(0, 16)
  form.cardNumber = digits.replace(/(\d{4})(?=\d)/g, '$1 ').trim()
}

function addMonths(date, months) {
  const d = new Date(date)
  d.setMonth(d.getMonth() + months)
  return d
}

function toDateOnly(d) {
  return d.toISOString().slice(0, 10)
}

async function pay() {
  statusText.value = ''
  statusClass.value = ''

  if (!form.cardName.trim() || form.cardNumber.replace(/\s/g, '').length < 12) {
    statusText.value = 'Please enter valid card details (demo only).'
    statusClass.value = 'status-error'
    return
  }

  paying.value = true
  statusText.value = 'Processing payment…'
  statusClass.value = 'status-pending'

  await new Promise((r) => setTimeout(r, 1200))

  try {
    const { data: { session } } = await supabase.auth.getSession()

    if (!session) {
      statusText.value = 'Please sign in before buying a pass.'
      statusClass.value = 'status-error'
      paying.value = false
      return
    }

    const { data: userRow, error: userErr } = await supabase
        .from('users')
        .select('user_id')
        .eq('email', session.user.email)
        .maybeSingle()

    if (userErr || !userRow) {
      statusText.value =
          'Could not find your user profile. Sign in with the same email used at registration.'
      statusClass.value = 'status-error'
      paying.value = false
      return
    }

    const now = new Date()
    const validUntil = addMonths(now, validityMonths[form.passType])
    const validFromStr = toDateOnly(now)
    const validUntilStr = toDateOnly(validUntil)

    const { error } = await supabase.from('passes').insert({
      user_id: userRow.user_id,
      route_id: Number(form.routeId),
      pass_type: passTypeMap[form.passType],
      valid_from: validFromStr,
      valid_until: validUntilStr,
      status: 'active',
      trips_used: 0,
      amount_saved: 0
    })

    if (error) {
      console.error(error)
      statusText.value = 'Could not save pass: ' + error.message
      statusClass.value = 'status-error'
      paying.value = false
      return
    }

    let emailSent = false

    try {
      const response = await supabase.functions.invoke(
          'send-payment-email',
          {
            body: {
              email: session.user.email,
              passType: passTypeMap[form.passType],
              route: routeLabel.value,
              validFrom: validFromStr,
              validUntil: validUntilStr,
              amount: selectedPrice.value
            }
          }
      )

      console.log('FULL FUNCTION RESPONSE:', response)

      if (response.error) {
        console.error('FUNCTION ERROR:', response.error)

        statusText.value =
            'Payment successful, but email failed. Check browser console.'
        statusClass.value = 'status-error'
      } else {
        emailSent = true

        statusText.value =
            'Payment successful! Confirmation email sent.'
        statusClass.value = 'status-on'
      }
    } catch (emailErr) {
      console.error('EMAIL INVOKE FAILED:', emailErr)

      statusText.value =
          'Payment successful, but could not send email.'
      statusClass.value = 'status-error'
    }

    if (emailSent) {
      setTimeout(() => {
        router.push('/pass')
      }, 1800)
    } else {
      setTimeout(() => {
        router.push('/pass')
      }, 4000)
    }

  } catch (err) {
    console.error(err)
    statusText.value = 'Something went wrong. Please try again.'
    statusClass.value = 'status-error'
  } finally {
    paying.value = false
  }
}
</script>

<style scoped>
* {
  box-sizing: border-box;
}

img,
video {
  max-width: 100%;
  height: auto;
}
.payment-page {
  padding-top: 40px;
  padding-bottom: 48px;
}

.page-head {
  margin-bottom: 28px;
}

.back-link {
  display: inline-block;
  font-size: 13.5px;
  font-weight: 600;
  color: white;
  margin-bottom: 14px;
}
.back-link:hover {
  color: #6B7280;
}

.page-head h1 {
  font-size: 28px;
  font-weight: 800;
  color: white;
  margin: 0 0 8px;
}

.page-sub {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.75);
  max-width: 520px;
}

.section-eyebrow {
  display: block;
  font-size: 12px;
  font-weight: 700;
  color: #1b2a52;
  letter-spacing: 0.06em;
  text-transform: uppercase;
  margin-bottom: 6px;
}

.payment-layout {
  display: grid;
  grid-template-columns: 1.3fr 1fr;
  gap: 24px;
  align-items: start;
  width: 100%;
}

.form-card,
.summary-card {
  padding: 28px 26px;
  background: linear-gradient(135deg, var(--navy-900) 0%, var(--navy-700) 45%, var(--green-600) 100%);
}
.form-card,
.summary-card {
  width: 100%;
  box-sizing: border-box;
}
.card-title {
  font-size: 18px;
  font-weight: 700;
  color: white;
  margin: 0 0 20px;
}

.field {
  margin-bottom: 16px;
  text-align: left;
}

.field label {
  display: block;
  font-size: 13px;
  font-weight: 700;
  color: white;
  margin-bottom: 6px;
}

.field input,
.field select {
  width: 100%;
  padding: 11px 12px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 10px;
  font-size: 14px;
  background: rgba(255, 255, 255, 0.95);
  color: #0e2246;
}

.field-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.status-box {
  padding: 12px 14px;
  border-radius: 12px;
  font-weight: 600;
  font-size: 13.5px;
  margin-bottom: 16px;
}

.status-pending { background: #fff3cd; color: #856404; }
.status-on { background: #d4edda; color: #155724; }
.status-error { background: #f8d7da; color: #721c24; }

.btn {
  width: 100%;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
  font-size: 14px;
  color: rgba(255, 255, 255, 0.85);
}

.summary-row strong {
  color: white;
  font-weight: 700;
  text-align: right;
  max-width: 60%;
}

.summary-row.total {
  border-bottom: none;
  margin-top: 8px;
  font-size: 16px;
}

.summary-row.total strong {
  color: #4ade80;
  font-size: 20px;
}

.summary-note {
  margin-top: 18px;
  font-size: 12.5px;
  color: rgba(255, 255, 255, 0.65);
  line-height: 1.5;
}

.summary-note code {
  font-size: 12px;
  background: rgba(0, 0, 0, 0.2);
  padding: 1px 5px;
  border-radius: 4px;
}

/* Mobile improvements */
@media (max-width: 768px) {
  .payment-page {
    padding: 20px 12px;
  }

  .payment-layout {
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .form-card,
  .summary-card {
    padding: 20px;
    border-radius: 16px;
  }

  .page-head h1 {
    font-size: 24px;
  }

  .page-sub {
    font-size: 13px;
  }

  .field-row {
    grid-template-columns: 1fr;
  }

  .summary-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 4px;
  }

  .summary-row strong {
    max-width: 100%;
    text-align: left;
  }
}

@media (max-width: 480px) {
  .payment-page {
    padding-top: 24px;
    padding-bottom: 32px;
  }
  .form-card,
  .summary-card {
    padding: 20px 18px;
  }
  .field-row {
    grid-template-columns: 1fr;
  }
  .page-head h1 {
    font-size: 24px;
  }
}
@media (max-width: 1024px) {
  .payment-layout {
    grid-template-columns: 1fr;
  }

  .summary-card {
    order: -1;
  }
}
@media (max-width: 480px) {
  .page-head h1 {
    font-size: 22px;
  }

  .card-title {
    font-size: 16px;
  }

  .btn {
    font-size: 14px;
    padding: 12px;
  }

  .field input,
  .field select {
    font-size: 16px;
  }
}
.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 16px;
}
</style>