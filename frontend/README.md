# GoShuttle Frontend

Vue 3 + Vue Router site shell for GoShuttle. Restructured from the original
app-style screens into a real website layout, with a shared blurred-glass
header/footer and glassmorphism panels throughout.

## Run it

```bash
npm install
npm run dev
```

Then open the local URL Vite prints (usually http://localhost:5173).

## Every page is built and wired together

- **`/`** — Home. Rebuilt from `home.html`: hero + live trip card + upcoming
  buses grid (each card links into `/schedule`).
- **`/track`** — Live Tracking. Rebuilt from `track.html`: a schematic route
  progress list (replaces the emoji "map grid") + bus info panel with a
  capacity bar.
- **`/schedule`** — Bus Schedule. Built as its own page (per your call) —
  search, status filter, expandable route cards with departure times and
  stop-by-stop detail.
- **`/stops-routes`** — Stops & Routes. Rebuilt from `sar.html`: search plus
  clickable/selectable stops (sets a "preferred stop" banner linking to the
  Schedule page).
- **`/pass`** — My Pass. Rebuilt from `bar.html`'s pass card, stats and "how
  to use your pass" steps. Copy-to-clipboard toast + animated stat counters.
- **`/sign-in`** — rebuilt from `index.html` with working field validation
  and a toast on submit.
- **`/register`** — rebuilt from `register.html` with the same field set
  (name, student number, CPUT email, campus, password, terms) and real
  validation (e.g. email must end in `mycput.ac.za`, password ≥ 8 chars).

All pages share the same header/footer, glass design language, and route
through Vue Router — this is one working site, not disconnected screens.

Note: the original `.js` files (`bar.js`, `sar.js`, `home.js`, `track.js`,
`LoginAndRegisterScript.js`) were empty when uploaded, so all interactivity
above (search, filtering, validation, toasts, counters) is new logic written
in Vue rather than a port of existing code.

## Structure

```
src/
  assets/theme.css       — design tokens, glass/button utilities, resets
  components/
    AppHeader.vue         — sticky blurred nav, restructured from bottom app-nav
    AppFooter.vue
  views/
    HomeView.vue          — from home.html
    TrackView.vue          — from track.html
    ScheduleView.vue       — new page (your call, see chat)
    StopsRoutesView.vue    — from sar.html
    MyPassView.vue         — from bar.html
    LoginView.vue           — from index.html
    RegisterView.vue        — from register.html
    NotFoundView.vue        — 404 (not one of your source pages)
    auth.css / placeholder.css — shared styles for the auth pages / 404
  router/index.js
```

## Notes

- No UI kit / animation library — everything is plain CSS transitions to
  keep the project light and easy to hand off.
- Color palette carries over your existing navy (`#142c56` / `#1b3b73`) and
  green (`#1c8c68` / `#17a04f`) brand colors rather than swapping in a
  different theme.
