// CDN cache-status tester.
// Fetches a static asset from the same origin and reports the CDN cache headers
// that Cloudflare adds (cf-cache-status, age, cache-control, cf-ray).
(function () {
  const btn = document.getElementById("cdn-test-btn");
  const out = document.getElementById("cdn-result");
  if (!btn || !out) return;

  const ASSET = "assets/img/cloud.svg";

  btn.addEventListener("click", async function () {
    btn.disabled = true;
    out.textContent = "Requesting " + ASSET + " ...";

    try {
      // Cache-bust the browser cache so the request actually reaches the CDN.
      const url = ASSET + "?t=" + Date.now();
      const res = await fetch(url, { cache: "no-store" });

      const headers = ["cf-cache-status", "age", "cache-control", "cf-ray", "server", "content-type"];
      const lines = ["HTTP " + res.status + " " + res.statusText, ""];
      for (const h of headers) {
        const v = res.headers.get(h);
        lines.push(h.padEnd(16) + ": " + (v == null ? "(not exposed)" : v));
      }
      lines.push("");
      lines.push("Tip: reload a few times — cf-cache-status should turn from MISS to HIT.");
      out.textContent = lines.join("\n");
    } catch (err) {
      out.textContent = "Request failed: " + err.message;
    } finally {
      btn.disabled = false;
    }
  });
})();
